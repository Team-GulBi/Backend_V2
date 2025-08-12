package com.gulbi.Backend.domain.rental.review.dto;

import java.util.List;

import lombok.Getter;

@Getter
public class ReviewsWithAvg {
	private List<ReviewWithAvg> reviews;

	public ReviewsWithAvg() {
	}

	public ReviewsWithAvg(List<ReviewWithAvg> reviews) {
		this.reviews = reviews;
	}
}
