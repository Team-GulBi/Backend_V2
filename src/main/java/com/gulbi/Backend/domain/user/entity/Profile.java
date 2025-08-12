package com.gulbi.Backend.domain.user.entity;


import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;
import com.gulbi.Backend.domain.user.dto.ProfileUpdateRequest;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private String phone; // 전화번호
    @Column(length = 2000)

    private String signature; // 전자서명


    public static Profile of(ImageUrl imageUrl, User user) {
        return Profile.builder()
                .user(user)
                .signature(imageUrl.getImageUrl())
                .build();
    }
    public void updateText(ProfileUpdateRequest dto) {
        this.phone = dto.getPhone();
    }
    public void updateSignature(ImageUrl url){
        this.signature=url.getImageUrl();
    }
}