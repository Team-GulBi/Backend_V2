package com.gulbi.Backend.domain.chat.message.dto;

import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageDto {
    private Long id;
    private String content;
    private Long senderId;    // 보낸 사람 ID
    private Long receiverId; //받은 사람 ID
    private Long chatRoomId;  // 채팅방 ID
    private LocalDateTime timestamp;  // 생성 시간

    // 생성자
    public ChatMessageDto(Long id, String content, Long senderId, Long receiverId, Long chatRoomId, LocalDateTime timestamp) {
        this.id = id;
        this.content = content;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.chatRoomId = chatRoomId;
        this.timestamp = timestamp;
    }
    public static ChatMessageDto from(ChatMessage chatMessage) {
        Long senderId = chatMessage.getSender() != null ? chatMessage.getSender().getId() : null;
        Long receiverId = chatMessage.getReceiver() != null ? chatMessage.getReceiver().getId() : null;

        return new ChatMessageDto(
                chatMessage.getId(),
                chatMessage.getContent(),
                senderId,
                receiverId, // receiverId를 안전하게 가져옴
                chatMessage.getChatRoom().getId(),
                chatMessage.getTimestamp()
        );
    }


    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}


