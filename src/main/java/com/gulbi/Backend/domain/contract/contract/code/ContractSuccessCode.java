package com.gulbi.Backend.domain.contract.contract.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ContractSuccessCode implements ResponseApiCode {

    CONTRACT_CREATE_SUCCESS(HttpStatus.OK, "C001", "계약서가 생성되었습니다."),
    CONTRACT_FOUNDED_SUCCESS(HttpStatus.OK, "C002", "계약서가 조회되었습니다."),
    CONTRACT_APPROVAL_SUCCESS(HttpStatus.OK, "C003", "계약이 성사 되었습니다."),
    CONTRACT_UNAPPROVAL_SUCCESS(HttpStatus.OK, "C004", "계약이 반려 되었습니다.(삭제되었습니다)");

    private HttpStatus status;
    private String code;
    private String message;

    ContractSuccessCode(HttpStatus status, String code, String message) {
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
