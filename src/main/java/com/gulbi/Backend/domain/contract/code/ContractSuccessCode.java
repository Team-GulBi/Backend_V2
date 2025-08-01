package com.gulbi.Backend.domain.contract.code;

import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public enum ContractSuccessCode implements ResponseApiCode {

    CONTRACT_CREATE_SUCCESS(HttpStatus.OK, "C001", "계약서가 생성되었습니다.");

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
