package com.gulbi.Backend.domain.rental.product.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ResponseApiCode {

    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "해당 상품 ID와 일치하는 상품을 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND_BY_TITLE(HttpStatus.NOT_FOUND, "E002", "해당 제목을 가진 상품이 존재하지 않습니다."),
    PRODUCT_NOT_FOUND_BY_TAGS(HttpStatus.NOT_FOUND, "E003", "해당 태그를 가진 상품이 존재하지 않습니다."),
    NO_UPDATED_COLUMNS(HttpStatus.NOT_FOUND, "E004", "변경 사항이 없습니다."),
    IMAGEURL_NOT_FOUND(HttpStatus.NOT_FOUND, "E005", "ImageUrlCollection에 값이 존재하지 않습니다."),
    MISSING_USER(HttpStatus.BAD_REQUEST, "E006", "사용자 정보가 누락되었습니다."),
    MISSING_CATEGORY(HttpStatus.BAD_REQUEST, "E007", "카테고리 정보가 누락되었습니다."),
    MISSING_TITLE(HttpStatus.BAD_REQUEST, "E008", "상품 제목이 누락되었습니다."),
    MISSING_NAME(HttpStatus.BAD_REQUEST, "E009", "상품 이름이 누락되었습니다."),
    INVALID_VIEWS(HttpStatus.BAD_REQUEST, "E010", "조회수는 0 이상이어야 합니다."),
    INVALID_PRICE(HttpStatus.BAD_REQUEST, "E011", "가격은 0 이상이어야 합니다."),
    MISSING_SIDO(HttpStatus.BAD_REQUEST, "E012", "시/도 정보가 누락되었습니다."),
    MISSING_SIGUNGU(HttpStatus.BAD_REQUEST, "E013", "시/군/구 정보가 누락되었습니다."),
    MISSING_DESCRIPTION(HttpStatus.BAD_REQUEST, "E014", "상품 설명이 누락되었습니다."),
    PRODUCT_VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "E100", "상품 유효성 검증에 실패했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E101", "데이터베이스 오류가 발생했습니다."),
    MISSING_REQUIRED_FIELD(HttpStatus.BAD_REQUEST, "E102", "필수 필드가 누락되었습니다."),
    UNSUPPORTED_SEARCH_CONDITION(HttpStatus.BAD_REQUEST, "E103", "지원하지 않는 검색 조건입니다."); // 추가된 부분


    private final HttpStatus status;
    private final String code;
    private final String message;

    ProductErrorCode(HttpStatus status, String code, String message) {
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
