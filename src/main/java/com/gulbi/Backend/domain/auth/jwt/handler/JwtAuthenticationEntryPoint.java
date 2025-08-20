package com.gulbi.Backend.domain.auth.jwt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gulbi.Backend.domain.auth.exception.AuthErrorCode;
import com.gulbi.Backend.global.response.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        ErrorResponse errorResponse = new ErrorResponse(
                AuthErrorCode.JWT_UNAUTHORIZED.getCode(),
                AuthErrorCode.JWT_UNAUTHORIZED.getMessage(),
                null
        );

        response.setStatus(AuthErrorCode.JWT_UNAUTHORIZED.getStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
