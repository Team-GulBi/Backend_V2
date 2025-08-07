package com.gulbi.Backend.domain.user.controller;


import com.gulbi.Backend.domain.user.dto.LoginRequest;
import com.gulbi.Backend.domain.user.dto.RegisterCommand;
import com.gulbi.Backend.domain.user.dto.RegisterRequest;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> register(@RequestPart RegisterRequest request,
        @RequestPart("signature")MultipartFile signature) throws IOException {
        RegisterCommand command = new RegisterCommand(signature,request);
        userService.register(command);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/signin")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        Map<String, String> response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}
