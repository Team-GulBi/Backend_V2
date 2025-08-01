package com.gulbi.Backend.global.util.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.List;

public class LogData {
    private static Logger logger = LoggerFactory.getLogger(LogData.class);
    private final Long timeStamp;
    private final String logLevel;
    private final String requestId;
    private final String className;
    private final String methodName;
    private final String userId;
    private final String httpMethod;
    private final Long executeTime;
    private final String message;
    private final List<String> methodArgs;

    public LogData(Long timeStamp, String logLevel, String requestId, String className, String methodName, String userId, String httpMethod, Long executeTime, String message, List<String> methodArgs) {
        this.timeStamp = timeStamp;
        this.logLevel = logLevel;
        this.requestId = requestId;
        this.className = className;
        this.methodName = methodName;
        this.userId = userId;
        this.httpMethod = httpMethod;
        this.executeTime = executeTime;
        this.message = message;
        this.methodArgs = methodArgs;
    }


    public static LogData of(Long timeStamp, String logLevel, String requestId, String className, String methodName, String userId, String httpMethod, Long executeTime, String message, List<String> methodArgs){
        return new LogData(timeStamp, logLevel, requestId, className, methodName, userId, httpMethod, executeTime, message, methodArgs);
    }

    public void print(){
        System.out.println("{"
                + "\"timestamp\": \"" + timeStamp + "\","
                + "\"level\": \"" + logLevel + "\","
                + "\"request_id\": \"" + requestId + "\","
                + "\"class_name\": \"" + className + "\","
                + "\"method_name\": \"" + methodName + "\","
                + "\"user_id\": \"" + userId + "\","
                + "\"http_method\": \"" + httpMethod + "\","
                + "\"response_time_ms\": \"" + executeTime + "\","
                + "\"args\": " + methodArgs + "}"
        );
    }
    public void log() {
        setMDC();
        String logMessage = "{" +
                "\"timestamp\": \"" + timeStamp + "\"," +
                "\"level\": \"" + logLevel + "\"," +
                "\"message\": \"" + message + "\"" +
                "}";

        switch (logLevel) {
            case "INFO":
                logger.info(logMessage);
                break;
            case "ERROR":
                logger.error(logMessage);
                break;
            default:
                logger.debug(logMessage);
                break;
        }
        MDC.clear();
    }

    private void setMDC() {
        MDC.put("requestId", this.requestId);
        MDC.put("className", this.className);
        MDC.put("methodName", this.methodName);
        MDC.put("userId", this.userId);
        MDC.put("httpMethod", this.httpMethod);
        MDC.put("executeTime", String.valueOf(this.executeTime));
        MDC.put("methodArgs", this.methodArgs.toString());
    }

}
