package com.gulbi.Backend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDto {
    private String nickname;
    private String email;
    private String password;
    private String phoneNumber;
}
