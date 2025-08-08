package com.gulbi.Backend.domain.contract.contract.code;

import org.springframework.http.HttpStatus;
import com.gulbi.Backend.global.response.ResponseApiCode;
import lombok.Getter;

@Getter
public enum ContractErrorCode implements ResponseApiCode {

	CONTRACT_NOT_FOUND(HttpStatus.NOT_FOUND, "C100", "계약서를 찾을 수 없습니다."),
	CONTRACT_DELETE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "C101", "계약서 삭제에 실패했습니다."),
	CURRENT_USER_NOT_PERMISSION(HttpStatus.INTERNAL_SERVER_ERROR, "C102", "해당 작업을 수행 할 권한이 없습니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ContractErrorCode(HttpStatus status, String code, String message) {
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
