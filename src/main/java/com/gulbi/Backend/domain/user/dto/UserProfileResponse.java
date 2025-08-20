package com.gulbi.Backend.domain.user.dto;

import com.gulbi.Backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private String phoneNumber;
    private String signature;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
            .phoneNumber(user.getPhoneNumber())
            .signature(user.getSignature())
            .build();
    }
}
