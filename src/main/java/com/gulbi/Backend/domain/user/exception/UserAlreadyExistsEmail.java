package com.gulbi.Backend.domain.user.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class UserAlreadyExistsEmail extends BusinessException {
    public UserAlreadyExistsEmail() {
        super(new ExceptionMetaData.Builder()
                .responseApiCode(UserErrorCode.USER_ALREADY_EXISTS_EMAIL)
                .build());
    }
}
