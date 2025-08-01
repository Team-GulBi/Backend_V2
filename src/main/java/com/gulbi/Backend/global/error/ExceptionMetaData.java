package com.gulbi.Backend.global.error;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gulbi.Backend.global.response.ResponseApiCode;
import org.springframework.http.HttpStatus;

public class ExceptionMetaData {
    private static final String NO_ARGS = "NO_ARGS_IN_CLASS";
    private static final String NO_STACKTRACE = "NO_STACKTRACE_IN_THIS_EXCEPTION";
    private static final String NO_CLASS_NAME = "NO_CLASS_NAME";
    private static final ResponseApiCode NO_RESPONSE_API_CODE = ExceptionMetaDataErrorCode.HAS_NO_RESPONSE_API_CODE;

    private String args;
    private String className;
    private String stackTrace;
    private ResponseApiCode responseApiCode;

    private ExceptionMetaData(Builder builder) {
        this.args = builder.args != null ? builder.args : NO_ARGS;
        this.className = builder.className != null ? builder.className : NO_CLASS_NAME;
        this.stackTrace = builder.stackTrace != null ? builder.stackTrace : NO_STACKTRACE;
        this.responseApiCode = builder.responseApiCode != null ? builder.responseApiCode : NO_RESPONSE_API_CODE;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public String getClassName() {
        return className;
    }

    public String getArgs() {
        return args;
    }

    public ResponseApiCode getResponseApiCode() {
        return responseApiCode;
    }

    public static class Builder {
        private String args;
        private String className;
        private String stackTrace;
        private ResponseApiCode responseApiCode;

        public Builder args(Object args) {
            this.args = ExceptionMetaDataHelper.convertExceptionArgsToJson(args);
            return this;
        }

        public Builder className(String className) {
            this.className = className;
            return this;
        }

        public Builder stackTrace(Throwable throwable) {
            this.stackTrace = ExceptionMetaDataHelper.extractStackTrace(throwable);
            return this;
        }

        public Builder responseApiCode(ResponseApiCode responseApiCode) {
            this.responseApiCode = responseApiCode;
            return this;
        }

        public ExceptionMetaData build() {
            return new ExceptionMetaData(this);
        }
    }

    enum ExceptionMetaDataErrorCode implements ResponseApiCode {
        HAS_NO_RESPONSE_API_CODE(HttpStatus.NOT_FOUND, "E001", "예외코드가 입력되지 않은 예외 입니다.");
        private final HttpStatus status;
        private final String code;
        private final String message;

        ExceptionMetaDataErrorCode(HttpStatus status, String code, String message) {
            this.status = status;
            this.code = code;
            this.message = message;
        }

        @Override
        public HttpStatus getStatus() {
            return this.status;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
