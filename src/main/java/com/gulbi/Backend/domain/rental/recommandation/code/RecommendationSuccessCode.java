package com.gulbi.Backend.domain.rental.recommandation.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum RecommendationSuccessCode implements ResponseApiCode {

    REALTIME_POPULAR_PRODUCTS_FOUND_SUCCESS(HttpStatus.OK ,"R001", "실시간 인기 상품 조회 완료."),
    PERSONAL_RECOMMENDATION_PRODUCTS_FOUND_SUCCESS(HttpStatus.OK ,"R002", "사용자 맞춤 상품 조회 완료"),
    LATEST_REGISTRATION_PRODUCTS_FOUND_SUCCESS(HttpStatus.OK ,"R003", "최근 등록 상품 조회 완료"),
    PRODUCTS_FOUND_BY_CATEGORIES_SUCCESS(HttpStatus.OK ,"R003", "해당 카테고리 관련 상품 조회 완료."),;

    private final HttpStatus status;
    private final String code;
    private final String message;

    RecommendationSuccessCode(HttpStatus status, String code, String message) {
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
