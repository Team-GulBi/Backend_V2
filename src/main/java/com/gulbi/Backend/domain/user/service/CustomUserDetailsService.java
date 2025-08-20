package com.gulbi.Backend.domain.user.service;

import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.exception.UserNotFoundException;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException());

        // UserDetails 객체를 반환
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .accountExpired(false) // 계정 만료 여부
                .accountLocked(false) // 계정 잠금 여부
                .credentialsExpired(false) // 자격 증명 만료 여부
                .disabled(false) // 계정 비활성화 여부
                .build();
    }
}
