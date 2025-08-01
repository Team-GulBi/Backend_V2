package com.gulbi.Backend.domain.rental.product.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.response.ResponseApiCode;

public abstract class ImageVoException extends BusinessException {
    public ImageVoException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }

    public static class ImageUrlNotFoundException extends ImageVoException {
        public ImageUrlNotFoundException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class NotValidatedImageUrlException extends ImageVoException {
        public NotValidatedImageUrlException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }

    public static class ImageCollectionIsEmptyException extends ImageVoException {
        public ImageCollectionIsEmptyException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }
}
