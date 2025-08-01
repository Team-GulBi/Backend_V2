package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;

import java.util.List;

public interface ReviewCrudService{
    public void saveReview(Review review);
    public List<ReviewWithAvgProjection> getReviewWithRateAvg(Long productId);
    public void deleteReview(Long reviewId);
    public void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto);
    public void removeAllReviewsFromProductId(Long productId);
}
