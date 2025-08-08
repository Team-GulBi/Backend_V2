package com.gulbi.Backend.domain.contract.application.code;

import org.springframework.http.HttpStatus;

import com.gulbi.Backend.global.response.ResponseApiCode;

public enum ApplicationErrorCode implements ResponseApiCode {
	APPLICATION_NOT_FOUND(HttpStatus.NOT_FOUND, "A100", "예약이 조회되지 않았습니다.");

	private HttpStatus status;
	private String code;
	private String message;

	ApplicationErrorCode(HttpStatus status, String code, String message) {
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
