package com.gulbi.Backend.domain.rental.recommandation.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public abstract class RecommendationException extends BusinessException {

    public RecommendationException(ExceptionMetaData metaDataDto) {
        super(metaDataDto);
    }

}
