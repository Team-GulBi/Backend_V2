package com.gulbi.Backend.domain.rental.review.dto;

import lombok.Getter;

@Getter
public class ReviewUpdateCommand {
	private ReviewUpdateRequest request;
	private Long reviewId;

	public ReviewUpdateCommand() {
	}

	public ReviewUpdateCommand(ReviewUpdateRequest request, Long reviewId) {
		this.request = request;
		this.reviewId = reviewId;
	}
}
