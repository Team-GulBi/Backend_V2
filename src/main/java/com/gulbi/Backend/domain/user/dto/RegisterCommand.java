package com.gulbi.Backend.domain.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class RegisterCommand {
	private MultipartFile signature;
	private RegisterRequest registerRequest;

	public RegisterCommand() {
	}

	public RegisterCommand(MultipartFile signature, RegisterRequest registerRequest) {
		this.signature = signature;
		this.registerRequest = registerRequest;
	}
}
