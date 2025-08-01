package com.gulbi.Backend.domain.user.service;

import com.gulbi.Backend.domain.user.dto.ProfileRequestDto;
import com.gulbi.Backend.domain.user.dto.ProfileResponseDto;
import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.exception.ProfileNotFoundException;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;
import com.gulbi.Backend.domain.user.repository.ProfileRepository;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.global.util.S3Uploader;
import com.gulbi.Backend.global.util.SecurityUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final S3Uploader s3Uploader;


    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository, JwtUtil jwtUtil,UserService userService, S3Uploader s3Uploader) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.userService=userService;
        this.s3Uploader=s3Uploader;
    }
//    public void createProfile(ProfileRequestDto request, UserDetails userDetails) {
//        // 이메일을 통해 User 객체를 찾기
//        User user = getUserByEmail(userDetails.getUsername());
//        Profile profile = Profile.fromDto(request, user);
//        profileRepository.save(profile);
//    }

    public String updateProfile(ProfileRequestDto request) {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User user = getUserByEmail(userDetails.getUsername());
        Profile existingProfile = getProfileByUser(user);
        // 프로필 업데이트
        existingProfile.update(request);
        profileRepository.save(existingProfile);
        // 프로필 완료 상태에 따라 역할을 결정하고 토큰 생성
        String role = determineUserRole(existingProfile);
        String token = generateUserToken(user, role);
        return token;
    }

    public String updateProfileImage(MultipartFile file) throws IOException {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User user = getUserByEmail(userDetails.getUsername());
        Profile existingProfile = getProfileByUser(user);

        // S3에 파일 업로드 후 URL 반환
        String imageUrl = s3Uploader.uploadFile(file, "profile-images");

        // 기존 프로필 정보를 유지하면서 image 필드만 업데이트
        ProfileRequestDto request = ProfileRequestDto.builder()
                .image(imageUrl)
                .intro(existingProfile.getIntro())
                .phone(existingProfile.getPhone())
                .signature(existingProfile.getSignature())
                .sido(existingProfile.getSido())
                .sigungu(existingProfile.getSigungu())
                .bname(existingProfile.getBname())
                .build();

        // 기존 updateProfile 메서드 활용 (JWT Role 업데이트 포함)
        return updateProfile(request);
    }

    public String updateProfileSignature(MultipartFile file) throws IOException {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User user = getUserByEmail(userDetails.getUsername());
        Profile existingProfile = getProfileByUser(user);

        // S3에 파일 업로드 후 URL 반환
        String signatureUrl = s3Uploader.uploadFile(file, "signatures");

        // 기존 프로필 정보를 유지하면서 signature 필드만 업데이트
        ProfileRequestDto request = ProfileRequestDto.builder()
                .image(existingProfile.getImage())
                .intro(existingProfile.getIntro())
                .phone(existingProfile.getPhone())
                .signature(signatureUrl)
                .sido(existingProfile.getSido())
                .sigungu(existingProfile.getSigungu())
                .bname(existingProfile.getBname())
                .build();

        // 기존 updateProfile 메서드 활용 (JWT Role 업데이트 포함)
        return updateProfile(request);
    }



    // 프로필 조회
    public ProfileResponseDto getProfile(Long userId) {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User loggedInUser = getUserByEmail(userDetails.getUsername());
        Long loggedInUserId = loggedInUser.getId();

        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));

        if (userId.equals(loggedInUserId)) {
            return ProfileResponseDto.fromProfileForUser(
                    profile.getImage(), profile.getIntro(), profile.getPhone(),
                    profile.getSignature(), profile.getSido(), profile.getSigungu(), profile.getBname());
        } else {
            return ProfileResponseDto.fromProfileForOtherUsers(
                    profile.getImage(), profile.getIntro(), profile.getSido(),
                    profile.getSigungu(), profile.getBname());
        }
    }
    
    public String getProfileImage(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found for user ID: " + userId));
        return profile.getImage();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Profile getProfileByUser(User user) {
        return profileRepository.findByUser(user)
                .orElseThrow(() -> new ProfileNotFoundException("Profile not found"));
    }

    private UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }

    private String determineUserRole(Profile profile) {
        return userService.isProfileComplete(profile) ? "ROLE_COMPLETED_USER" : "ROLE_INCOMPLETED_USER";
    }

    private String generateUserToken(User user, String role) {
        return jwtUtil.generateToken(user.getEmail(), user.getId(), role);
    }



}

