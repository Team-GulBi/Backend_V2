package com.gulbi.Backend.domain.chat.message.service;

import com.gulbi.Backend.domain.chat.message.dto.MessageReceiveRequest;
import com.gulbi.Backend.domain.chat.message.dto.MessageSaveCommand;
import com.gulbi.Backend.domain.chat.message.dto.MessageSendResponse;
import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;
import com.gulbi.Backend.domain.chat.message.repository.ChatMessageRepository;
import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.chat.websocket.WebSocketEventHandler;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.global.config.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final WebSocketEventHandler webSocketEventHandler;
    private final RabbitTemplate rabbitTemplate;

    // 메시지 저장 및 오프라인 사용자 처리
    public ChatMessage saveMessage(MessageSaveCommand command) {

        // 상대방이 현재 채팅방에 있는지 확인 (user1과 user2 비교)
        ChatRoom room = command.getRoom();
        User sender = command.getSender();
        User receiver = command.getReceiver();
        boolean isRecipientOnline = checkRecipientOnline(room, sender);
        log.info("{} isRecipientOnline", isRecipientOnline);
        // 메시지 생성 및 저장
        ChatMessage chatMessage = ChatMessage.builder()
            .content(command.getRequest().getContent())
            .sender(sender)
            .receiver(receiver)
            .chatRoom(room)
            .timestamp(LocalDateTime.now())
            .build();
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
        MessageSendResponse responseMessage = MessageSendResponse.from(savedMessage);

        // 상대방이 오프라인일 경우 메시지를 RabbitMQ 큐에 추가
        if (!isRecipientOnline) {
            sendMessageToQueue(room,responseMessage);
            return savedMessage;
        }
        // 상대가 온라인이면 읽음처리로 바꿔서 내보냄
        // ToDo: 메시지 수신을 기준으로 읽도록 변경
        chatMessage.readMessage();
        return savedMessage;
    }

    // 특정 채팅방에 있는 메시지 목록 가져오기
    public List<MessageSendResponse> getMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId)
                .stream()
                .map(MessageSendResponse::from)  // ChatMessage -> MessageReceiveRequest 변환
                .toList();
    }

    // 메시지를 읽음 상태로 변경
    public void markMessageAsRead(Long messageId) {
        ChatMessage message = chatMessageRepository.findById(messageId)
                .orElseThrow(() -> new IllegalArgumentException("메시지를 찾을 수 없습니다."));
        message.markAsRead();
        chatMessageRepository.save(message);
    }

    // 메시지를 RabbitMQ로 전송
    public void sendMessageToQueue(ChatRoom room, MessageSendResponse message) {
        String routingKey = "chat.room." + room.getId();
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey, message);
        log.info("Sent Message to Exchange: {}, RoutingKey: {}", RabbitMQConfig.EXCHANGE_NAME, routingKey);
    }

    // 상대방이 온라인인지 확인 (sender와 반대되는 사용자의 상태 확인)
    private boolean checkRecipientOnline(ChatRoom chatRoom, User sender) {
        if (chatRoom.getUser1().getId().equals(sender.getId())) {
            return webSocketEventHandler.isUserOnline(chatRoom.getUser2().getId());
        } else {
            return webSocketEventHandler.isUserOnline(chatRoom.getUser1().getId());
        }
    }

}
