package com.gulbi.Backend.domain.rental.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewUpdateRequestDto {
    private final Long reviewId;
    private final Integer rating;
    private final String content;
}
