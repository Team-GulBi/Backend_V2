package com.gulbi.Backend.global.response;

import org.springframework.http.HttpStatus;

public interface ResponseApiCode {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
