package com.gulbi.Backend.domain.chat.room.service;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.chat.room.repository.ChatRoomRepository;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepoService;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepoService userRepoService;

    public List<ChatRoom> findChatRoomsByUserId(Long userId) {
        return chatRoomRepository.findByUser1IdOrUser2Id(userId, userId);
    }
    public Optional<ChatRoom> findById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId);
    }
    // 채팅방 가져오기 (ID로 검색)
    public ChatRoom getChatRoomById(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom with ID " + chatRoomId + " not found."));
    }

    public ChatRoom findOrCreateChatRoom(Long user1Id, Long user2Id) {
        User user1 = userRepoService.findById(user1Id);
        User user2 = userRepoService.findById(user2Id);

        // user1Id 또는 user2Id가 포함된 채팅방 가져오기
        List<ChatRoom> existingRooms = chatRoomRepository.findByUser1IdOrUser2Id(user1.getId(), user2.getId());

        // 두 유저가 정확히 매칭된 채팅방이 있는지 확인
        Optional<ChatRoom> existingRoom = existingRooms.stream()
                .filter(room -> (room.getUser1().getId().equals(user1.getId()) && room.getUser2().getId().equals(user2.getId()))
                        || (room.getUser1().getId().equals(user2.getId()) && room.getUser2().getId().equals(user1.getId())))
                .findFirst();

        if (existingRoom.isPresent()) {
            return existingRoom.get();
        }

        // 채팅방이 없으면 새로 생성
        ChatRoom newRoom = new ChatRoom(user1, user2);
        ChatRoom savedRoom = chatRoomRepository.save(newRoom);

        // 큐 및 Exchange 설정
        createQueueAndBind(String.valueOf(savedRoom.getId()));
        return savedRoom;
    }

    // 큐 및 Exchange 생성 메서드
    private void createQueueAndBind(String chatRoomId) {
        String queueName = "chat.queue." + chatRoomId;
        String routingKey = "chat.room." + chatRoomId;

        // 큐와 Exchange를 선언하고 바인딩
        rabbitTemplate.execute(channel -> {
            channel.queueDeclare(queueName, true, false, false, null);  // 큐 생성
            channel.exchangeDeclare("chat.exchange", "topic", true);  // Exchange 생성
            channel.queueBind(queueName, "chat.exchange", routingKey);  // 큐와 Exchange 바인딩
            return null;
        });
    }
}
