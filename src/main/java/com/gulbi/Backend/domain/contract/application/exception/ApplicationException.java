package com.gulbi.Backend.domain.contract.application.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class ApplicationException extends BusinessException {
	public ApplicationException(ExceptionMetaData metaDataDto) {
		super(metaDataDto);
	}
}
