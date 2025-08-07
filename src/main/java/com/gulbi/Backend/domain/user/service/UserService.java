package com.gulbi.Backend.domain.user.service;

import com.gulbi.Backend.domain.user.dto.LoginRequest;
import com.gulbi.Backend.domain.user.dto.ProfileCreateCommand;
import com.gulbi.Backend.domain.user.dto.RegisterCommand;
import com.gulbi.Backend.domain.user.dto.RegisterRequest;
import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.repository.ProfileRepoService;
import com.gulbi.Backend.domain.user.repository.UserRepoService;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.domain.user.entity.User;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepoService userRepoService;
    private final ProfileRepoService profileRepoService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;

    @Transactional
    public void register(RegisterCommand command) throws IOException {
        RegisterRequest request = command.getRegisterRequest();
        String encodedPassword = passwordEncoder.encode(request.getPassword()); // 객체 생성 전 인코딩 처리

        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(encodedPassword) // 인코딩된 비번 넣기
                .phoneNumber(request.getPhoneNumber())
                .build();
        userRepoService.save(user);

        ProfileCreateCommand profileCreateCommand = new ProfileCreateCommand(user, command.getSignature());
        profileService.createProfile(profileCreateCommand);
    }

    @Transactional(readOnly = true)
    public Map<String, String> login(LoginRequest request) {
        User user = findByEmail(request.getEmail());
        Profile profile = findProfileByUser(user);
        String role = determineUserRole(profile);

        String token = jwtUtil.generateToken(user.getEmail(), user.getId(), role);

        // ID와 토큰을 함께 반환
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("id", String.valueOf(user.getId()));  // ID를 문자열로 변환하여 저장


        return response;
    }

    // public boolean isProfileComplete(Profile profile) {
    //     return profile.getImage() != null && profile.getIntro() != null && profile.getPhone() != null &&
    //             profile.getSignature() != null && profile.getSido() != null && profile.getSigungu() != null &&
    //             profile.getBname() != null; //협의해야할듯 어떤필드 여부를 따질지
    // }

    public User getAuthenticatedUser() {
        String email = getAuthenticatedEmail();
        return userRepoService.findByEmail(email);
    }


    private String getAuthenticatedEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("No authenticated user");
        }
    }
    public User findByEmail(String email) {
        return userRepoService.findByEmail(email);

    }
    public User getUserById(Long id) {
        return userRepoService.findById(id);

    }
    // 닉네임 반환 메서드 (ID 기반)
    public String getNicknameById(Long userId) {
        User user = userRepoService.findById(userId);

        return user.getNickname();
    }
    //Spring Security 인증 처리
    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
    //프로필 조회
    private Profile findProfileByUser(User user) {
        return profileRepoService.findByUser(user);
    }

    private String determineUserRole(Profile profile) {
        return "ROLE_COMPLETED_USER";
    }



}
