package com.gulbi.Backend.domain.rental.review.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class ReviewException extends BusinessException {
    public ReviewException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }
}
