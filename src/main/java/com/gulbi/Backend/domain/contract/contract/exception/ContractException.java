package com.gulbi.Backend.domain.contract.contract.exception;

import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

public class ContractException extends BusinessException {
	public ContractException(ExceptionMetaData metaData) {
		super(metaData);
	}



}
