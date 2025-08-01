package com.gulbi.Backend.domain.rental.product.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public abstract class ImageException extends BusinessException {
    public ImageException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }

    public static class NotUploadImageToS3Exception extends ImageException {
        public NotUploadImageToS3Exception(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class NotContainedImageIdException extends ImageException {
        public NotContainedImageIdException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class ImageDeleteValidationException extends ImageException {
        public ImageDeleteValidationException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class ImageDeleteFailedException extends ImageException {
        public ImageDeleteFailedException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class InvalidProductImageIdException extends ImageException {
        public InvalidProductImageIdException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class DatabaseErrorException extends ImageException {
        public DatabaseErrorException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }
}
