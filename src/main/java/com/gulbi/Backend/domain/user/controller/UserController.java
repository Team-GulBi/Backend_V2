package com.gulbi.Backend.domain.user.controller;

import com.gulbi.Backend.domain.user.dto.SignupRequest;
import com.gulbi.Backend.domain.user.dto.UserProfileResponse;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> signup(@RequestPart SignupRequest request,
                                               @RequestPart("signature") MultipartFile signature) throws IOException {
        userService.signup(request, signature);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProfile(@RequestPart(value = "file", required = false) MultipartFile signature,
                                              @RequestParam(required = false) String phoneNumber) throws IOException {
        userService.updateProfile(signature, phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<UserProfileResponse> getProfile() {
        UserProfileResponse userResponse = userService.getProfile();
        return ResponseEntity.ok(userResponse);
    }
}
