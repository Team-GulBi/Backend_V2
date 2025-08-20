package com.gulbi.Backend.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.gulbi.Backend.domain.auth.jwt.JwtTokenProvider;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

//인증 정보 유효성 검증 클래스
@Component
public class SecurityUtil {
    
    private static JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        SecurityUtil.jwtTokenProvider = jwtTokenProvider;
    }
        
    public static UserDetails getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("인증 정보가 없습니다.");
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof UserDetails)) {
            throw new IllegalStateException("잘못된 인증 정보입니다.");
        }

        return (UserDetails) principal;
    }
    
    public static Long getCurrentUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = jwtTokenProvider.resolveToken(request);
            if (token != null) {
                try {
                    return jwtTokenProvider.extractMemberIdFromAccessToken(token);
                } catch (Exception e) {
                    throw new UserNotFoundException();
                }
            }
        }
        throw new UserNotFoundException();
    }
}
