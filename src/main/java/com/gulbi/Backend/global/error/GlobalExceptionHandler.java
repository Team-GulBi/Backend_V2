package com.gulbi.Backend.global.error;

import com.gulbi.Backend.domain.user.response.ErrorCode;
import com.gulbi.Backend.global.response.RestApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<RestApiResponse> handleBusinessException(BusinessException e){
        RestApiResponse response = new RestApiResponse(e.getMetaData().getResponseApiCode());
        if (!Objects.equals(e.getMetaData().getStackTrace(), "") &&
                !Objects.equals(e.getMetaData().getStackTrace(), "NO_STACKTRACE_IN_THIS_EXCEPTION")) {
            MDC.put("exceptionCauseArgs", e.getMetaData().getArgs());
            MDC.put("exceptionClass", e.getMetaData().getClassName());
            MDC.put("stackTrace",e.getMetaData().getStackTrace());
            logger.error(e.getMessage());
            return ResponseEntity.status(e.getMetaData().getResponseApiCode().getStatus()).body(response);
        }

        MDC.put("exceptionClass", e.getMetaData().getClassName());
        MDC.put("stackTrace",e.getMetaData().getStackTrace());
        logger.warn(e.getMessage());
        return ResponseEntity.status(e.getMetaData().getResponseApiCode().getStatus()).body(response);

    }


}
