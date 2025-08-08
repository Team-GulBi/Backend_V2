package com.gulbi.Backend.global.error;

import org.springframework.http.HttpStatus;

import com.gulbi.Backend.global.response.ResponseApiCode;

public enum InfraErrorCode implements ResponseApiCode {

	DB_EXCEPTION(HttpStatus.NOT_FOUND, "C100", "데이터 베이스에서 에러가 발생했습니다."),
	S3_BUCKET_EXCEPTION(HttpStatus.NOT_FOUND, "C100", "s3업로드 중 문제가 발생 했습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	InfraErrorCode(HttpStatus status, String code, String message) {
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
