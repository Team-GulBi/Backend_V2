package com.gulbi.Backend.domain.contract.application.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ApplicationSuccessCode implements ResponseApiCode {
    APPLICATION_CREATE_SUCCESS(HttpStatus.OK, "A001", "예약이 성공적으로 등록 되었습니다."),
    APPLICATION_FOUND_SUCCESS(HttpStatus.OK, "A002", "예약이 성공적으로 조회 되었습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ApplicationSuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
