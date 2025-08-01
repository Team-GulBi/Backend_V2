package com.gulbi.Backend.domain.rental.review.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class ReviewException extends BusinessException {
    // ExceptionMetaData를 기반으로 예외 생성
    public ReviewException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }

    public static class ReviewNotFoundException extends ReviewException {
        public ReviewNotFoundException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class ReviewValidationException extends ReviewException {
        public ReviewValidationException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class DatabaseErrorException extends ReviewException {
        public DatabaseErrorException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class MissingReviewFiledException extends ReviewException {
        public MissingReviewFiledException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }
}
