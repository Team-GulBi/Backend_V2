package com.gulbi.Backend.domain.user.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class UserAlreadyExistsName extends BusinessException {
    public UserAlreadyExistsName() {
        super(new ExceptionMetaData.Builder()
                .responseApiCode(UserErrorCode.USER_ALREADY_EXISTS_NAME)
                .build());
    }
}
