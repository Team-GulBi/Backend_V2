package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;

import java.util.List;

public interface ReviewRepoService {
    Review findById(Long reviewId);
    void save(Review review);
    List<ReviewWithAvgProjection> getReviewWithRateAvgById(Long productId);
    void delete(Long reviewId);
    void removeAllReviewsByProductId(Long productId);
}
