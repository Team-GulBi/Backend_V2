package com.gulbi.Backend.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExceptionMetaDataHelper {
    private static final String CANT_PARSE_ARGS_TO_JSON = "Error converting args to JSON";
    public static String extractStackTrace(Throwable throwable) {
        StringBuilder stackTraceBuilder = new StringBuilder();
        for (StackTraceElement element : throwable.getStackTrace()) {
            stackTraceBuilder.append(element).append("\n");
        }
        return stackTraceBuilder.toString();
    }

    public static String convertExceptionArgsToJson(Object args) {
        try {
            return new ObjectMapper().writeValueAsString(args);
        } catch (Exception e) {
            return CANT_PARSE_ARGS_TO_JSON;
        }
    }
}
