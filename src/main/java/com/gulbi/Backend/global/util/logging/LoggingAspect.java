package com.gulbi.Backend.global.util.logging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    private String userEmail;
    private String className;

    @Around("execution(* com.gulbi.Backend.domain.rental.product.controller.*.*(..)) " +
            "or execution(* com.gulbi.Backend.domain.rental.review.controller.*.*(..)) " +
            "or execution(* com.gulbi.Backend.domain.rental.*.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {


        userEmail = getUserEmail();
        className = joinPoint.getSignature().toShortString();
        MDC.put("userEmail",userEmail);
        MDC.put("className",className);
        long startTime = System.currentTimeMillis();


        logger.info("Status: started");


        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        MDC.put("executionTime", String.valueOf(executionTime));
        logger.info("Status: ended");
        MDC.clear();
        return proceed;
    }

    private String getUserEmail(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userpk = "Unknown";

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            userpk = user.getUsername();
        }
        return userpk;
    }
}
