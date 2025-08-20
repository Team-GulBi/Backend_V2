package com.gulbi.Backend.domain.rental.recommandation.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class LokiException extends BusinessException {
    
    public LokiException(ExceptionMetaData exceptionMetaData) {
        super(exceptionMetaData);
    }
    
    public static class ResponseException extends LokiException {
        public ResponseException(ExceptionMetaData exceptionMetaData) {
            super(exceptionMetaData);
        }
    }
}
