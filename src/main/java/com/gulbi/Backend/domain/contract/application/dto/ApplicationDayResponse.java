package com.gulbi.Backend.domain.contract.application.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ApplicationDayResponse {
	private List<ApplicationStatusDetailResponse> status;
	private boolean isOwner;

	@Override
	public String toString() {
		return "ApplicationDayResponse{" +
			"status=" + status +
			", isOwner=" + isOwner +
			'}';
	}

	public ApplicationDayResponse() {
	}

	public ApplicationDayResponse(List<ApplicationStatusDetailResponse> status, boolean isOwner) {
		this.status = status;
		this.isOwner = isOwner;
	}
}
