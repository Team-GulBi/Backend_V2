package com.gulbi.Backend.domain.user.service;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.user.dto.ProfileCreateCommand;
import com.gulbi.Backend.domain.user.dto.ProfileResponse;
import com.gulbi.Backend.domain.user.dto.ProfileUpdateCommand;
import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.ProfileRepoService;
import com.gulbi.Backend.domain.user.repository.UserRepoService;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.global.util.S3Uploader;
import com.gulbi.Backend.global.util.SecurityUtil;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Service
public class ProfileService {

    private final UserRepoService userRepoService;
    private final ProfileRepoService profileRepoService;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;


    public ProfileService(UserRepoService userRepoService, ProfileRepoService profileRepoService, JwtUtil jwtUtil, S3Uploader s3Uploader) {
		this.userRepoService = userRepoService;
		this.profileRepoService = profileRepoService;
        this.jwtUtil = jwtUtil;
        this.s3Uploader=s3Uploader;
    }
       public void createProfile(ProfileCreateCommand command) throws IOException {
           // 이메일을 통해 User 객체를 찾기
           User user = command.getUser();
           ImageUrl imageUrl = ImageUrl.of(updateProfileSignatureToS3(command.getSignature()));
           //나머지 정보는 사용자 자율에 맡겨 수정하도록
           Profile profile = Profile.of(imageUrl, user);
           profileRepoService.save(profile);
       }

    @Transactional
    public void updateProfile(ProfileUpdateCommand command) throws IOException {
        Long profileId = command.getProfileId();
        Profile existingProfile = getProfileById(profileId);

        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User user = getUserByEmail(userDetails.getUsername());

        if (!Objects.equals(existingProfile.getUser().getId(), user.getId())) {
            throw new AccessDeniedException("프로필 소유자가 아닙니다.");
        }

        //URL VO로 매핑
        ImageUrl signature = ImageUrl.of(updateProfileSignatureToS3(command.getChangedSignature()));
        //프로필 업데이트
        existingProfile.updateSignature(signature);
        existingProfile.updateText(command.getProfileInfo());
        profileRepoService.save(existingProfile);

    }


    @Transactional(readOnly = true)
    public ProfileResponse getProfile(Long userId) {
        UserDetails userDetails = SecurityUtil.getAuthenticatedUser();
        User loggedInUser = getUserByEmail(userDetails.getUsername());
        Profile profile = profileRepoService.findByUserId(userId);
        Long profileLoggedInUserId = profile.getUser().getId();
        Long loggedInUserId = loggedInUser.getId();
        boolean isOwned =Objects.equals(profileLoggedInUserId, loggedInUserId);
        return ProfileResponse.from(profile, isOwned);

    }

    private String updateProfileSignatureToS3(MultipartFile file) throws IOException {
        // S3에 파일 업로드 후 URL 반환
        return s3Uploader.uploadFile(file, "signatures");
    }

    private User getUserByEmail(String email) {
        return userRepoService.findByEmail(email);
    }

    private Profile getProfileById(Long profileId) {
        return profileRepoService.findById(profileId);

    }

    private UserDetails getAuthenticatedUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserDetails) authentication.getPrincipal();
    }



    private String generateUserToken(User user) {
        return jwtUtil.generateToken(user.getEmail(), user.getId());
    }



}

