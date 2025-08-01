package com.gulbi.Backend.domain.rental.product.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum CategoryErrorCode implements ResponseApiCode {

    NOT_INITIALIZED_CATEGORIES(HttpStatus.NOT_FOUND,"E001","카테고리가 초기화 되지 않았습니다."),
    NOT_FOUND_CATEGORY(HttpStatus.NOT_FOUND,"E002","해당 CategoryId와 대응되는 카테고리가 없습니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;

    CategoryErrorCode(HttpStatus status, String code, String message) {
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
