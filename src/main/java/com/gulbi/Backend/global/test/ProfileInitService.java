package com.gulbi.Backend.global.test;

import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.ProfileRepository;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileInitService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public void initProfile(){
        List<User> users = userRepository.findAll();

        for(User user : users) {
            Profile profile = Profile.builder()
                    .bname("bName")
                    .image("https://sdddd")
                    .intro("메로미")
                    .phone("010-1234-5678")
                    .sido("서울특별시")
                    .signature("https://dd")
                    .sigungu("노원구 상계동")
                    .user(user).build();
            profileRepository.save(profile);
        }
//            private final String image; // 프로필 이미지 URL
//            private final String intro; // 자기소개
//            private final String phone; // 전화번호
//            private final String signature; // 전자서명
//            private final String sido; // 시도
//            private final String sigungu; // 시군구
//            private final String bname; // 읍면동
//        }

    }
}
