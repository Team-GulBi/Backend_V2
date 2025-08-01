package com.gulbi.Backend.domain.user.entity;


import com.gulbi.Backend.domain.user.dto.ProfileRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 프로필 ID (PK)

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user; // 사용자의 외래키 (FK)

    private String image; // 프로필 이미지 URL
    private String intro; // 자기소개
    private String phone; // 전화번호
    private String signature; // 전자서명

    @Size(max = 100) // 시도 최대 길이 제한
    private String sido; // 시도

    @Size(max = 100) // 시군구 최대 길이 제한
    private String sigungu; // 시군구

    @Size(max = 100) // 읍면동 최대 길이 제한
    private String bname; // 읍면동

    // ProfileRequestDto와 User를 받아서 Profile 객체를 생성하는 정적 메서드
    public static Profile fromDto(ProfileRequestDto dto, User user) {
        return Profile.builder()
                .user(user)
                .image(dto.getImage())
                .intro(dto.getIntro())
                .phone(dto.getPhone())
                .signature(dto.getSignature())
                .sido(dto.getSido())
                .sigungu(dto.getSigungu())
                .bname(dto.getBname())
                .build();
    }
    public void update(ProfileRequestDto dto) {
        this.image = dto.getImage();
        this.intro = dto.getIntro();
        this.phone = dto.getPhone();
        this.signature = dto.getSignature();
        this.sido = dto.getSido();
        this.sigungu = dto.getSigungu();
        this.bname = dto.getBname();
    }
}