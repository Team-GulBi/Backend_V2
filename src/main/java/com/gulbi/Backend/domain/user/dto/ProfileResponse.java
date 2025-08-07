package com.gulbi.Backend.domain.user.dto;

import com.gulbi.Backend.domain.user.entity.Profile;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {

    private String phone;
    private String signature;
    private Boolean isOwned;

    public static ProfileResponse from(Profile profile, boolean isOwner) {
        return ProfileResponse.builder()
            .phone(isOwner ? profile.getPhone() : null)
            .signature(isOwner ? profile.getSignature() : null)
            .isOwned(isOwner)
            .build();
    }

}