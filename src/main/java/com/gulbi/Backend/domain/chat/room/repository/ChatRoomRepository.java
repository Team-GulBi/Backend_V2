package com.gulbi.Backend.domain.chat.room.repository;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 특정 사용자가 포함된 채팅방 조회
    List<ChatRoom> findByUser1IdOrUser2Id(Long user1Id, Long user2Id);
}