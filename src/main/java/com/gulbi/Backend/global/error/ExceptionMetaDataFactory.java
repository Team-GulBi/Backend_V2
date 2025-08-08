package com.gulbi.Backend.global.error;

import com.gulbi.Backend.global.response.ResponseApiCode;

public class ExceptionMetaDataFactory {

	public static ExceptionMetaData of(Object args, String className, Throwable e, ResponseApiCode code) {
		Object safeArgs = args != null ? args : "No args provided";
		String safeClassName = className != null ? className : "UnknownClass";
		Throwable safeThrowable = e != null ? e : new Throwable("No stack trace available");

		return new ExceptionMetaData.Builder()
			.args(safeArgs)
			.className(safeClassName)
			.stackTrace(safeThrowable)
			.responseApiCode(code)
			.build();
	}



}
