package com.gulbi.Backend.domain.rental.review.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ReviewErrorCode implements ResponseApiCode {
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"E001","해당 reviewId와 일치하는 리뷰가 존재하지 않습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "Review 관련 데이터베이스 오류가 발생했습니다.");
    ;
    private final HttpStatus status;
    private final String code;
    private final String message;

    ReviewErrorCode(HttpStatus status, String code, String message) {
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
