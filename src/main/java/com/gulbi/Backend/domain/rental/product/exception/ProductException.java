package com.gulbi.Backend.domain.rental.product.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.response.ResponseApiCode;

public class ProductException extends BusinessException {
    public ProductException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }
}
