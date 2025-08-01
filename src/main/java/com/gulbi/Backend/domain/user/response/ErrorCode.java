package com.gulbi.Backend.domain.user.response;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ErrorCode implements ResponseApiCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"E001","존재하지않는 유저입니다.");
    private HttpStatus status;
    private String code;
    private String message;


    ErrorCode(HttpStatus status, String code, String message) {
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
