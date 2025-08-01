package com.gulbi.Backend.domain.rental.recommandation.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum WebClientErrorCode implements ResponseApiCode {
    WEB_CLIENT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "WEB001", "잘못된 엔드포인트 혹은 쿼리입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "WEB002", "서버 내부 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    WebClientErrorCode(HttpStatus status, String code, String message) {
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
