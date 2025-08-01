package com.gulbi.Backend.domain.user.entity;

import com.gulbi.Backend.domain.chat.room.entity.ChatRoom;
import com.gulbi.Backend.domain.user.dto.RegisterRequestDto;
import com.gulbi.Backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor  // 기본 생성자
@AllArgsConstructor  // 모든 필드를 사용하는 생성자
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    // 내가 만든 채팅방 (user1 역할)
    @OneToMany(mappedBy = "user1", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoom> createdChatRooms = new ArrayList<>();

    // 내가 참여한 채팅방 (user2 역할)
    @OneToMany(mappedBy = "user2", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChatRoom> joinedChatRooms = new ArrayList<>();

    // 빌더 패턴을 사용해 DTO 기반 객체 생성
    @Builder
    public static User fromDto(RegisterRequestDto dto, String encodedPassword) {
        return User.builder()
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .password(encodedPassword)
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }
}
