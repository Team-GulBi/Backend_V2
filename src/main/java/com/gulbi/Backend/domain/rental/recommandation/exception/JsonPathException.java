package com.gulbi.Backend.domain.rental.recommandation.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public abstract class JsonPathException extends BusinessException {
    public JsonPathException(ExceptionMetaData metaDataDto) {
        super(metaDataDto);
    }
    public static class jsonPathCantParseQueryException extends JsonPathException {
        public jsonPathCantParseQueryException(ExceptionMetaData metaDataDto) {
            super(metaDataDto);
        }
    }

}
