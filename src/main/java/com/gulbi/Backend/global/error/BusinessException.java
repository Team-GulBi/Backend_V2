package com.gulbi.Backend.global.error;

import com.gulbi.Backend.global.response.ResponseApiCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class BusinessException extends RuntimeException{
    private ExceptionMetaData metaData;

    public BusinessException(ExceptionMetaData metaDataDto){
        super(metaDataDto.getResponseApiCode().getMessage());
        this.metaData = metaDataDto;
    }

    public ExceptionMetaData getStatus() {
        return metaData;
    }
}


