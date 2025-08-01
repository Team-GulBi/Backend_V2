package com.gulbi.Backend.global.test;

import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserInitService {

    private final UserRepository userRepository;

    public UserInitService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createDummyUser(){

        int phoneNum=10;
        String email = "aasd";
        String nickName = "jhon";
        String password = "pass";
        for(int i=0; i<50; i++ ){
            User user = User.builder()
                    .nickname(nickName + String.valueOf(phoneNum))
                    .email(email+String.valueOf(phoneNum)+"@gogle.com")
                    .password(password+String.valueOf(phoneNum)) // 인코딩된 비번 넣기
                    .phoneNumber("010-1234-42"+String.valueOf(phoneNum))
                    .build();
            phoneNum ++;
            userRepository.save(user);
        }
    }
}
