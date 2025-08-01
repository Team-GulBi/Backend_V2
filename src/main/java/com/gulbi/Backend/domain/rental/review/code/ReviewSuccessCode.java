package com.gulbi.Backend.domain.rental.review.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ReviewSuccessCode implements ResponseApiCode {
    REVIEW_REGISTER_SUCCESS(HttpStatus.OK, "R001", "리뷰가 성공적으로 등록 되었습니다."),
    REVIEW_DELETED_SUCCESS(HttpStatus.OK, "R001", "리뷰가 성공적으로 삭제 되었습니다."),
    REVIEW_INFO_UPDATED_SUCCESS(HttpStatus.OK, "R004", "리뷰 정보가 성공적으로 갱신 되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ReviewSuccessCode(HttpStatus status, String code, String message) {
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
