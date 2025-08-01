package com.gulbi.Backend.domain.rental.product.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.response.ResponseApiCode;

public abstract class ProductException extends BusinessException {
    public ProductException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }

    public static class ProductNotFoundException extends ProductException {
        public ProductNotFoundException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class NoProductFoundForTitleException extends ProductException {
        public NoProductFoundForTitleException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class NoProductFoundForTagsException extends ProductException {
        public NoProductFoundForTagsException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class NoUpdateProductException extends ProductException {
        public NoUpdateProductException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class MissingProductFieldException extends ProductException {
        public MissingProductFieldException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class ProductValidationException extends ProductException {
        public ProductValidationException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class DatabaseErrorException extends ProductException {
        public DatabaseErrorException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class InvalidProductSearchDetailException extends ProductException {
        public InvalidProductSearchDetailException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }
}
