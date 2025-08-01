package com.gulbi.Backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ProfileRequestDto {
    private String image; // 프로필 이미지 URL
    private String intro; // 자기소개
    private String phone; // 전화번호
    private String signature; // 전자서명
    private String sido; // 시도
    private String sigungu; // 시군구
    private String bname; // 읍면동
}