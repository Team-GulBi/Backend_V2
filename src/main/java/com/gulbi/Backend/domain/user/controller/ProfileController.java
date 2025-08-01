package com.gulbi.Backend.domain.user.controller;

import com.gulbi.Backend.domain.user.dto.ProfileRequestDto;
import com.gulbi.Backend.domain.user.dto.ProfileResponseDto;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.ProfileService;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.util.S3Uploader;
import com.gulbi.Backend.global.util.SecurityUtil;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

//    @PostMapping  삭제할거임
//    public ResponseEntity<String> createProfile(@RequestBody ProfileRequestDto request) {
//        // 현재 인증된 사용자 가져오기
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // 인증된 사용자 정보 출력
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//        // 로그인한 사용자 정보를 바탕으로 프로필 생성 로직 호출
//        profileService.createProfile(request, userDetails);
//        return ResponseEntity.ok("Profile created successfully.");
//    }

    @PatchMapping
    public ResponseEntity<String> updateProfile(@RequestBody ProfileRequestDto request) {

        // ProfileService에서 JWT 토큰을 반환받음
        String newToken = profileService.updateProfile(request);
        // 응답 헤더에 토큰 추가 (jwt role 변경으로 유효하지 않은 헤더정보를 최신화해줘야함)

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newToken)
                .body("Profile updated successfully");
    }

    @PatchMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfileImage(@RequestParam("image") MultipartFile file) throws IOException {
        String newToken = profileService.updateProfileImage(file);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newToken)
                .body("Profile image updated successfully.");
    }

    @PatchMapping(value = "/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProfileSignature(@RequestParam("file") MultipartFile file) throws IOException {
        String newToken = profileService.updateProfileSignature(file);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newToken)
                .body("Signature updated successfully.");
    }


    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        // 요청된 userId를 바탕으로 프로필을 조회
        ProfileResponseDto profileResponseDto = profileService.getProfile(userId);
        return ResponseEntity.ok(profileResponseDto);
    }

    @GetMapping("/mine")
    public ResponseEntity<ProfileResponseDto> getMyProfile() {
        // 현재 로그인된 사용자 정보 가져오기
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User loggedInUser = profileService.getUserByEmail(userDetails.getUsername());

        // 본인 프로필 조회
        ProfileResponseDto profileResponseDto = profileService.getProfile(loggedInUser.getId());

        return ResponseEntity.ok(profileResponseDto);
    }


}