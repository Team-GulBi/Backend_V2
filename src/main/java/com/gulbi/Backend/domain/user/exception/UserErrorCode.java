package com.gulbi.Backend.domain.user.exception;

import com.gulbi.Backend.global.response.ResponseApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ResponseApiCode {
    
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS_EMAIL(HttpStatus.CONFLICT, "U002", "이미 존재하는 이메일입니다."),
    USER_ALREADY_EXISTS_NAME(HttpStatus.CONFLICT, "U003", "이미 존재하는 닉네임입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
