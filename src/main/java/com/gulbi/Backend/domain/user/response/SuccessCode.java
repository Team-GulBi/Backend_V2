package com.gulbi.Backend.domain.user.response;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum SuccessCode implements ResponseApiCode {
    REGISTER_SUCCESS(HttpStatus.CREATED,"M001","회원가입이 성공적으로 완료되었습니다."),
    LOGIN_SUCCESS(HttpStatus.OK,"M002","로그인이 성공적으로 완료되었습니다");

    private HttpStatus status;
    private String code;
    private String message;


    SuccessCode(HttpStatus status, String code, String message) {
        this.status=status;
        this.code=code;
        this.message=message;
    }

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
