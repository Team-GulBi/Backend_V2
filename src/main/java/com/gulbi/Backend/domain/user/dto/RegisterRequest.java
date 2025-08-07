package com.gulbi.Backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
}
