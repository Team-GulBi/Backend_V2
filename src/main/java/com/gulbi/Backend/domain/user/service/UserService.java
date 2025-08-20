package com.gulbi.Backend.domain.user.service;

import com.gulbi.Backend.domain.user.dto.SignupRequest;
import com.gulbi.Backend.domain.user.dto.UserProfileResponse;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.exception.UserAlreadyExistsEmail;
import com.gulbi.Backend.domain.user.exception.UserAlreadyExistsName;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.global.util.S3Uploader;
import com.gulbi.Backend.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final S3Uploader s3Uploader;

    @Transactional
    public void signup(SignupRequest request, MultipartFile signature) throws IOException {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsEmail();
        }

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new UserAlreadyExistsName();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encodedPassword)
                .phoneNumber(request.getPhoneNumber())
                .build();
        
        if (signature != null && !signature.isEmpty()) {
            String signatureUrl = updateProfileSignatureToS3(signature);
            user.setSignature(signatureUrl);
        }
        
        userRepository.save(user);
    }

    private String updateProfileSignatureToS3(MultipartFile file) throws IOException {
        return s3Uploader.uploadFile(file, "signatures");
    }

    @Transactional
    public void updateProfile(MultipartFile signature, String phoneNumber) throws IOException {
        User currentUser = getAuthenticatedUser();
        
        if (phoneNumber != null) {
            currentUser.setPhoneNumber(phoneNumber);
        }
        
        if (signature != null && !signature.isEmpty()) {
            String signatureUrl = updateProfileSignatureToS3(signature);
            currentUser.setSignature(signatureUrl);
        }
        
        userRepository.save(currentUser);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getProfile() {
        User currentUser = getAuthenticatedUser();
        return UserProfileResponse.from(currentUser);
    }

    @Transactional(readOnly = true)
    public User getAuthenticatedUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        return userRepository.findById(userId).orElseThrow(() -> 
            new UserNotFoundException());
    }
}
