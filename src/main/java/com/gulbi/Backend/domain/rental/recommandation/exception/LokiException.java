package com.gulbi.Backend.domain.rental.recommendation.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public abstract class LokiException extends BusinessException {
    public LokiException(ExceptionMetaData metaData) {
        super(metaData);
    }

    public static class ResponseException extends LokiException {
        public ResponseException(ExceptionMetaData metaData) {
            super(metaData);
        }
    }

    public static class RequestException extends LokiException {
        public RequestException(ExceptionMetaData metaData) {
            super(metaData);
        }
    }
}
