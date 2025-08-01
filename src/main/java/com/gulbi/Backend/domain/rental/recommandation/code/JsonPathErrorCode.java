package com.gulbi.Backend.domain.rental.recommandation.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum JsonPathErrorCode implements ResponseApiCode {
    JSONPATH_CANT_PARSE_QUERY(HttpStatus.BAD_REQUEST,"J001","쿼리결과에서 데이터를 가져오지 못했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    JsonPathErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
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
