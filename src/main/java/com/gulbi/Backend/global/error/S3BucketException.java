package com.gulbi.Backend.global.error;

public class S3BucketException extends InfrastructureException{
	public S3BucketException(ExceptionMetaData metaDataDto) {
		super(metaDataDto);
	}
}
