package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;

import java.util.List;

public interface ReviewRepoService {
    public void saveReview(Review review);
    public List<ReviewWithAvgProjection> getReviewWithRateAvg(Long productId);
    public void deleteReview(Long reviewId);
    public void updateReview(ReviewUpdateRequest reviewUpdateRequest);
    public void removeAllReviewsFromProductId(Long productId);
}
