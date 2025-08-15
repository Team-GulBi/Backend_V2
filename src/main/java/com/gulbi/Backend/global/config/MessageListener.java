package com.gulbi.Backend.global.config;

import com.gulbi.Backend.domain.chat.message.dto.MessageReceiveRequest;
import com.gulbi.Backend.domain.chat.message.dto.MessageSendResponse;
import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;
import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.chat.room.service.ChatRoomService;
import com.gulbi.Backend.domain.chat.websocket.WebSocketEventHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessageListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final RabbitTemplate rabbitTemplate;
    private final WebSocketEventHandler webSocketEventHandler;
    private final ChatRoomService chatRoomService;
    private final RabbitMQConfig rabbitMQConfig;

    // 중복 메시지 방지를 위한 처리 상태 추적
    private final Set<Long> processedMessages = new HashSet<>();


    // 구독 이벤트 처리
    //구독을 하자마자 발생하는 이벤트임 !!
    @EventListener
    public void onUserSubscribed(SessionSubscribeEvent event) {
        // StompHeaderAccessor를 사용하여 세션 ID 추출
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId(); // 세션 ID 가져오기
        Long recevierId = webSocketEventHandler.getUserIdBySessionId(sessionId); // 세션 ID를 통해 사용자 ID 가져오기
        if (recevierId == null) {
            log.warn("Session {} not associated with any user.", sessionId);
            return;
        }

        // 구독한 destination이 채팅방과 관련된 것인지 확인
        String destination = headerAccessor.getDestination();
        if (destination == null || !destination.startsWith("/sub/chat/room/")) {
            return;
        }

        // chatRoomId 추출
        Long chatRoomId = Long.valueOf(destination.replace("/sub/chat/room/", ""));
        log.info("User {} subscribed to chat room: {}", recevierId, chatRoomId);

        // 🔹 채팅방별로 동적 큐 생성 & 바인딩
        String queueName = "chat.queue." + chatRoomId;
        Queue queue = rabbitMQConfig.createQueue(chatRoomId.toString());
        Binding binding = rabbitMQConfig.bindQueueToExchange(queue);

        // 큐에서 메시지 가져오기 (큐가 비어있을 때까지)
        MessageSendResponse chatMessage;
        while ((chatMessage = (MessageSendResponse) rabbitTemplate.receiveAndConvert(queueName)) != null) {
            log.debug("Dequeued message for chat room {}: {}", chatRoomId, chatMessage);

            // 채팅방에서 상대방 ID 가져오기
            Long receiverId = findReceiverIdFromChatRoom(chatMessage.getChatRoomId(), chatMessage.getSenderId());

            // 수신자가 현재 연결된 사용자이고, 메시지가 처리되지 않았다면 전송
            if (receiverId.equals(recevierId) && !processedMessages.contains(chatMessage.getMessageId())) {
                chatMessage.readMessage();
                log.info("Delivering queued message to user {}: {}", recevierId, chatMessage);
                sendToWebSocket(chatMessage);
                processedMessages.add(chatMessage.getMessageId());
            } else {
                storeMessageForLater(chatMessage);
            }
        }
    }


    // WebSocket으로 메시지 전송
    private void sendToWebSocket(MessageSendResponse chatMessage) {
        log.debug("Sending message via WebSocket to chat room {}: {}", chatMessage.getChatRoomId(),
            chatMessage);
        messagingTemplate.convertAndSend(
                "/sub/chat/room/" + chatMessage.getChatRoomId(),
            chatMessage
        );
        log.info("Message sent to WebSocket for chat room {}: {}",chatMessage.getChatRoomId(),
            chatMessage);
    }

    // 메시지를 큐에 저장
    private void storeMessageForLater(MessageSendResponse chatMessage) {
        log.debug("Storing message back in queue: {}", chatMessage);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, chatMessage);
        log.info("Message requeued: {}", chatMessage);
    }



    // 채팅방에서 상대방 ID 찾기
    private Long findReceiverIdFromChatRoom(Long chatRoomId, Long senderId) {
        ChatRoom chatRoom = chatRoomService.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found with id: " + chatRoomId));

        if (chatRoom.getUser1().getId().equals(senderId)) {
            return chatRoom.getUser2().getId();
        } else if (chatRoom.getUser2().getId().equals(senderId)) {
            return chatRoom.getUser1().getId();
        } else {
            throw new IllegalArgumentException("SenderId does not match any users in the chat room");
        }
    }
}
