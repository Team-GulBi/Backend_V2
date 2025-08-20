package com.gulbi.Backend.domain.auth.exception;

import com.gulbi.Backend.global.response.ResponseApiCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ResponseApiCode {
    
    AUTH_INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "유효하지 않은 리프레시 토큰입니다."),
    JWT_ACCESS_DENIED(HttpStatus.FORBIDDEN, "A002", "접근 권한이 없습니다."),
    JWT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A003", "인증되지 않은 사용자입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}

