package com.gulbi.Backend.domain.user.controller;

import com.gulbi.Backend.domain.rental.product.code.ProductSuccessCode;
import com.gulbi.Backend.domain.user.dto.ProfileResponse;
import com.gulbi.Backend.domain.user.dto.ProfileUpdateCommand;
import com.gulbi.Backend.domain.user.dto.ProfileUpdateRequest;
import com.gulbi.Backend.domain.user.service.ProfileService;
import com.gulbi.Backend.global.response.RestApiResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;


    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PatchMapping(value = "/{profileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestApiResponse> updateProfile(@RequestPart("file") MultipartFile signature,
        @RequestPart("text")ProfileUpdateRequest request,
        @PathVariable("profileId")Long profileId) throws IOException {
        ProfileUpdateCommand command = new ProfileUpdateCommand(request, signature,profileId);
        profileService.updateProfile(command);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_REGISTER_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable Long userId) {
        ProfileResponse profileResponse = profileService.getProfile(userId);
        return ResponseEntity.ok(profileResponse);
    }


}