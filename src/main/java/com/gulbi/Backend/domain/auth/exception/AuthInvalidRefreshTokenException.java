package com.gulbi.Backend.domain.auth.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class AuthInvalidRefreshTokenException extends BusinessException {
    public AuthInvalidRefreshTokenException() {
        super(new ExceptionMetaData.Builder()
                .responseApiCode(AuthErrorCode.AUTH_INVALID_REFRESH_TOKEN)
                .build());
    }
}
