package com.gulbi.Backend.global.error;

public class DatabaseException extends InfrastructureException{
	public DatabaseException(ExceptionMetaData metaDataDto) {
		super(metaDataDto);
	}
}
