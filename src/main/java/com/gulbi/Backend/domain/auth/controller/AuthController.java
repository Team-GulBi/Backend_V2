package com.gulbi.Backend.domain.auth.controller;

import com.gulbi.Backend.domain.auth.dto.LoginRequest;
import com.gulbi.Backend.domain.auth.dto.LoginResponse;
import com.gulbi.Backend.domain.auth.dto.TokenRefreshResponse;
import com.gulbi.Backend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request, 
                                             HttpServletResponse response) {
        LoginResponse loginResponse = authService.login(request, response);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refreshToken(HttpServletRequest request,
                                                           HttpServletResponse response) {
        TokenRefreshResponse refreshResponse = authService.refreshToken(request, response);
        return ResponseEntity.ok(refreshResponse);
    }
}
