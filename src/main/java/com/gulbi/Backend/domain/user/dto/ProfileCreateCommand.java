package com.gulbi.Backend.domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

import com.gulbi.Backend.domain.user.entity.User;

import lombok.Getter;

@Getter
public class ProfileCreateCommand {
	private User user;
	private MultipartFile signature;

	public ProfileCreateCommand() {
	}

	public ProfileCreateCommand(User user, MultipartFile signature) {
		this.user = user;
		this.signature = signature;
	}
}
