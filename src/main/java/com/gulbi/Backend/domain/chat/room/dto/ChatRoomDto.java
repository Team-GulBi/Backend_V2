package com.gulbi.Backend.domain.chat.room.dto;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private Long user1Id;
    private String user1Nickname;
    private Long user2Id;
    private String user2Nickname;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ChatRoomDto fromEntity(ChatRoom chatRoom) {
        return new ChatRoomDto(
                chatRoom.getId(),
                chatRoom.getUser1().getId(),
                chatRoom.getUser1().getNickname(),
                chatRoom.getUser2().getId(),
                chatRoom.getUser2().getNickname(),
                chatRoom.getCreatedAt(),
                chatRoom.getUpdatedAt()
        );
    }
}