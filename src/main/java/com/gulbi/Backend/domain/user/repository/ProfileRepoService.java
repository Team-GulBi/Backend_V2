package com.gulbi.Backend.domain.user.repository;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
@Component
public class ProfileRepoService {
	private final ProfileRepository profileRepository;

	public ProfileRepoService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}

	public Profile findByUser(User user){
		return profileRepository.findByUser(user).orElseThrow();
	}
	public Profile findById(Long id){
		return profileRepository.findById(id).orElseThrow();
	}
	public Profile findByUserId(Long id){
		return profileRepository.findByUserId(id).orElseThrow();
	}

	public Profile save(Profile profile){
		return profileRepository.save(profile);
	}
	public Profile update(Profile profile){
		return profileRepository.save(profile);
	}
}
