package com.gulbi.Backend.global.error;

import org.springframework.http.HttpStatus;

import com.gulbi.Backend.global.response.ResponseApiCode;

public enum GlobalErrorCode implements ResponseApiCode {
	LIST_IS_EMPTY(HttpStatus.NOT_FOUND, "G100", "빈 리스트 입니다.");


	private final HttpStatus status;
	private final String code;
	private final String message;

	GlobalErrorCode(HttpStatus status, String code, String message) {
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
