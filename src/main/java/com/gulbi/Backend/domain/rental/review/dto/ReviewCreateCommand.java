package com.gulbi.Backend.domain.rental.review.dto;

import lombok.Getter;

@Getter
public class ReviewCreateCommand {
	private ReviewCreateRequest request;
	private Long productId;

	public ReviewCreateCommand() {
	}

	public ReviewCreateCommand(ReviewCreateRequest request, Long productId) {
		this.request = request;
		this.productId = productId;
	}
}
