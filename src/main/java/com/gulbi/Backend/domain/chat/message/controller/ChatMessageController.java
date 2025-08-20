package com.gulbi.Backend.domain.chat.message.controller;

import com.gulbi.Backend.domain.auth.jwt.JwtTokenProvider;
import com.gulbi.Backend.domain.chat.message.dto.MessageReceiveRequest;
import com.gulbi.Backend.domain.chat.message.dto.MessageSaveCommand;
import com.gulbi.Backend.domain.chat.message.dto.MessageSendResponse;
import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;
import com.gulbi.Backend.domain.chat.message.service.ChatMessageService;
import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.chat.room.repository.ChatRoomRepository;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessageSendingOperations messagingTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅방 메시지 목록 조회
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<MessageSendResponse>> getMessages(@PathVariable Long chatRoomId) {
        List<MessageSendResponse> messages = chatMessageService.getMessages(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    // 메시지 읽음 처리
    @PatchMapping("/{messageId}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long messageId) {
        chatMessageService.markMessageAsRead(messageId);
        return ResponseEntity.noContent().build();
    }

    // 웹소켓 메시지 처리

    @MessageMapping("/chat/message/room/{roomId}")
    public void sendMessage(
        @Payload MessageReceiveRequest request,
        @DestinationVariable Long roomId,
        @Header("Authorization") String authorization
    ){

            // JWT에서 Sender User 추출
            String token = authorization.replace("Bearer ", "");
            Long senderId = jwtTokenProvider.extractMemberIdFromAccessToken(token);
            User sender = userRepository.findById(senderId)
                    .orElseThrow(() -> new UserNotFoundException());

            // Receiver User 추출
            ChatRoom room = chatRoomRepository.findById(roomId).orElseThrow();
            User receiver = room.getUser2();

            MessageSaveCommand command = new MessageSaveCommand(request,sender, receiver, room);

            // 메시지 저장 (DB 저장)
            ChatMessage savedMessage = chatMessageService.saveMessage(command);

            // 특정 구독자들에게 메시지 전달
            messagingTemplate.convertAndSend(
                    "/sub/chat/room/" + roomId,
                    MessageSendResponse.from(savedMessage) // 저장된 메시지를 DTO로 변환 후 전달
            );
        }
}