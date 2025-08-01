package com.gulbi.Backend.domain.rental.product.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ImageErrorCode implements ResponseApiCode {

    CANT_UPLOAD_IMAGE_TO_S3(HttpStatus.INTERNAL_SERVER_ERROR,"E001","이미지를 S3에 업로드 중 문제가 발생 했습니다."),
    NOT_CONTAINED_IMAGE_ID(HttpStatus.BAD_REQUEST,"E002","Image Id 값이 전달되지 않았습니다."),

    IMAGE_DELETE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "E200", "이미지 삭제에 실패했습니다."),
    INVALID_IMAGE_ID(HttpStatus.BAD_REQUEST, "E201", "잘못된 이미지 ID가 제공되었습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "이미지 관련 데이터베이스 오류가 발생했습니다."),
    NOT_VALIDATED_IMAGE_URL(HttpStatus.BAD_REQUEST, "E002", "올바르지 않은 이미지 URL 형식 입니다."),
    IMAGE_COLLECTION_IS_EMPTY(HttpStatus.BAD_REQUEST, "E002", "ImageCollection 안에 이미지가 존재하지 않습니다.");




    private final HttpStatus status;
    private final String code;
    private final String message;

    ImageErrorCode(HttpStatus status, String code, String message) {
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
