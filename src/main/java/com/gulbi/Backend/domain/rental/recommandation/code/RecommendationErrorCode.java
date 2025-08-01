package com.gulbi.Backend.domain.rental.recommandation.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum RecommendationErrorCode implements ResponseApiCode {
    REALTIME_POPULAR_PRODUCT_DOES_NOT_EXIST(HttpStatus.OK,"E001","실시간 인기상품이 존재하지 않습니다.(최근에 검색 된 기록이 없습니다.)");

    private final HttpStatus status;
    private final String code;
    private final String message;

    RecommendationErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public org.springframework.http.HttpStatus getStatus() {
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
