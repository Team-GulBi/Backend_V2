package com.gulbi.Backend.domain.user.repository;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.user.entity.User;
@Component
public class UserRepoService {
	private final UserRepository userRepository;

	public UserRepoService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public User findById(Long id){
		return userRepository.findById(id).orElseThrow();
	}
	public User findByEmail(String email){
		return userRepository.findByEmail(email).orElseThrow();
	}

	public User save(User user){
		return userRepository.save(user);
	}
	public User update(User user){
		return userRepository.save(user);
	}
}
