package com.gulbi.Backend.domain.chat.message.repository;

import com.gulbi.Backend.domain.chat.message.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdOrderByTimestampAsc(Long chatRoomId);
    List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
