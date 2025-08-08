package com.gulbi.Backend.global.error;

public class InfrastructureException extends RuntimeException{

	private ExceptionMetaData metaData;

	public InfrastructureException(ExceptionMetaData metaDataDto){
		super(metaDataDto.getResponseApiCode().getMessage());
		this.metaData = metaDataDto;
	}

	public ExceptionMetaData getStatus() {
		return metaData;
	}
}
