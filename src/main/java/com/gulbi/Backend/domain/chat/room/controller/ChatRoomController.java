package com.gulbi.Backend.domain.chat.room.controller;

import com.gulbi.Backend.domain.chat.room.dto.ChatRoomDto;
import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.chat.room.service.ChatRoomService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final UserService userService;
    // 채팅방 생성 또는 반환
    @PostMapping("/chatrooms")
    public ResponseEntity<ChatRoomDto> findOrCreateChatRoom(@RequestParam String user1Email, @RequestParam String user2Email) {
        ChatRoom chatRoom = chatRoomService.findOrCreateChatRoom(user1Email, user2Email);
        return ResponseEntity.ok(ChatRoomDto.fromEntity(chatRoom));
    }

    @GetMapping("/chatrooms")
    public ResponseEntity<List<ChatRoomDto>> getMyChatRooms() {
        User currentUser = userService.getAuthenticatedUser(); // 인증된 사용자 가져오기
        List<ChatRoom> chatRooms = chatRoomService.findChatRoomsByUserId(currentUser.getId());

        List<ChatRoomDto> chatRoomDtos = chatRooms.stream()
                .map(ChatRoomDto::fromEntity)
                .toList();

        return ResponseEntity.ok(chatRoomDtos);
    }

}

