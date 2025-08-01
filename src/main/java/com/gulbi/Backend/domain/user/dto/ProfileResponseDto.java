package com.gulbi.Backend.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponseDto {
    private String image;
    private String intro;
    private String phone;
    private String signature;
    private String sido;
    private String sigungu;
    private String bname;

    // 타인의 프로필을 위한 정적 팩토리 메서드 (phone과 signature는 null로 설정)
    public static ProfileResponseDto fromProfileForOtherUsers(String image, String intro, String sido, String sigungu, String bname) {
        return ProfileResponseDto.builder()
                .image(image)
                .intro(intro)
                .phone(null)  // 타인에게는 전화번호를 표시하지 않음
                .signature(null)  // 타인에게는 전자서명을 표시하지 않음
                .sido(sido)
                .sigungu(sigungu)
                .bname(bname)
                .build();
    }

    // 본인의 프로필을 위한 정적 팩토리 메서드 (모든 정보 포함)
    public static ProfileResponseDto fromProfileForUser(String image, String intro, String phone, String signature, String sido, String sigungu, String bname) {
        return ProfileResponseDto.builder()
                .image(image)
                .intro(intro)
                .phone(phone)
                .signature(signature)
                .sido(sido)
                .sigungu(sigungu)
                .bname(bname)
                .build();
    }
}