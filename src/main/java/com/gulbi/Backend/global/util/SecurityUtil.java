package com.gulbi.Backend.global.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//인증 정보 유효성 검증 클래스
public class SecurityUtil {

    private SecurityUtil() {}

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
}
