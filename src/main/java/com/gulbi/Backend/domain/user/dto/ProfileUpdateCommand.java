package com.gulbi.Backend.domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class ProfileUpdateCommand {
	private ProfileUpdateRequest profileInfo;
	private MultipartFile changedSignature;
	private Long profileId;

	public ProfileUpdateCommand() {
	}

	public ProfileUpdateCommand(ProfileUpdateRequest profileInfo, MultipartFile changedSignature, Long profileId) {
		this.profileInfo = profileInfo;
		this.changedSignature = changedSignature;
		this.profileId = profileId;
	}
}
