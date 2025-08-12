package com.gulbi.Backend.domain.rental.product.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class ImageException extends BusinessException {
    public ImageException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }

}
