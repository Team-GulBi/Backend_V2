package com.gulbi.Backend.domain.rental.review.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewUpdateRequest {
    private final Integer rating;
    private final String content;
}
