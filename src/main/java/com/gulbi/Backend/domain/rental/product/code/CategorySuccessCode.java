package com.gulbi.Backend.domain.rental.product.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum CategorySuccessCode implements ResponseApiCode {

     GET_CATEGORY_SUCCESS(HttpStatus.CREATED,"C001","카테고리 조회가 성공적으로 완료되었습니다."),
     DELETE_CATEGORY_OK(HttpStatus.OK,"C002","카테고리가 성공적으로 삭제되었습니다."),
     CATEGORY_CREATE_SUCCESS(HttpStatus.CREATED,"C003","카테고리 등록이 성공적으로 완료되었습니다.");


    private HttpStatus status;
    private String code;
    private String message;

    CategorySuccessCode(HttpStatus status, String code, String message) {
        this.status = status;
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
