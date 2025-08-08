package com.gulbi.Backend.domain.contract.application.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ApplicationCalendarResponse {
	private List<ApplicationStatusResponse> status;
	private boolean isOwner;

	@Override
	public String toString() {
		return "ApplicationCalendarResponse{" +
			"status=" + status +
			", isOwner=" + isOwner +
			'}';
	}

	public ApplicationCalendarResponse() {
	}

	public ApplicationCalendarResponse(List<ApplicationStatusResponse> status, boolean isOwner) {
		this.status = status;
		this.isOwner = isOwner;
	}
}
