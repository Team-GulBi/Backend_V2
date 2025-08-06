package com.gulbi.Backend.domain.rental.application.dto;

import java.time.LocalDateTime;

import com.gulbi.Backend.domain.rental.application.entity.ApplicationStatus;

import lombok.Getter;

@Getter
public class ApplicationStatusDetailResponse {
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private ApplicationStatus status;
	private Long applicationId;

	public ApplicationStatusDetailResponse() {
	}

	public ApplicationStatusDetailResponse(LocalDateTime startDate, LocalDateTime endDate, ApplicationStatus status,
		Long applicationId) {
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.applicationId = applicationId;
	}

	@Override
	public String toString() {
		return "ApplicationStatusDetailResponse{" +
			"startDate=" + startDate +
			", endDate=" + endDate +
			", status=" + status +
			", applicationId=" + applicationId +
			'}';
	}
}
