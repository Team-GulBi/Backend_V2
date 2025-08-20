package com.gulbi.Backend.domain.auth.service;

import com.gulbi.Backend.domain.auth.dto.LoginRequest;
import com.gulbi.Backend.domain.auth.dto.LoginResponse;
import com.gulbi.Backend.domain.auth.dto.TokenRefreshResponse;
import com.gulbi.Backend.domain.auth.exception.AuthInvalidRefreshTokenException;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.domain.auth.jwt.JwtTokenProvider;
import com.gulbi.Backend.domain.user.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request, HttpServletResponse response) {

        authenticateUser(request.getEmail(), request.getPassword());
        
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> 
            new UserNotFoundException());

        String accessToken = jwtTokenProvider.generateAccessToken(user.getEmail(), user.getId());
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail(), user.getId());

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public void logout(HttpServletResponse response) {

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);

        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0);
        response.addCookie(refreshTokenCookie);

    }

    @Transactional(readOnly = true)
    public TokenRefreshResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }
        
        if (refreshToken == null) {
            throw new AuthInvalidRefreshTokenException();
        }
        
        String email = jwtTokenProvider.extractEmailFromRefreshToken(refreshToken);
        
        if (!jwtTokenProvider.isRefreshTokenValid(refreshToken)) {
            throw new AuthInvalidRefreshTokenException();
        }
        
        User user = userRepository.findByEmail(email).orElseThrow(() -> 
            new UserNotFoundException());

        String newAccessToken = jwtTokenProvider.generateAccessToken(email, user.getId());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(email, user.getId());
        
        // 새로운 refreshToken을 쿠키에 설정
        Cookie refreshTokenCookie = new Cookie("refreshToken", newRefreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(refreshTokenCookie);
        
        return TokenRefreshResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}
