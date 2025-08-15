package com.gulbi.Backend.domain.chat.message.entity;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime timestamp;
    private boolean isRead;
    private boolean isOnline;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(String content, User sender, User receiver, ChatRoom chatRoom, LocalDateTime timestamp, boolean isOnline) {
        this.content = content;
        this.sender = sender;
        this.receiver = receiver;
        this.chatRoom = chatRoom;
        this.timestamp = timestamp;
        this.isOnline = isOnline;
        this.isRead = false;  // 기본값으로 읽지 않음
    }

    public void readMessage(){
        this.isRead=true;
    }

    public void markAsRead() {
        this.isRead = true;
    }
}
