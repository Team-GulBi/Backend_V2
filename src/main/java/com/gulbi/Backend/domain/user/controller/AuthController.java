package com.gulbi.Backend.domain.user.controller;


import com.gulbi.Backend.domain.user.dto.LoginRequestDto;
import com.gulbi.Backend.domain.user.dto.RegisterRequestDto;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDto request) {
        userService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto request) {
        Map<String, String> response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
