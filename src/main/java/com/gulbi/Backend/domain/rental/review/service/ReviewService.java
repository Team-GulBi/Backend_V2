package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public void addReviewToProduct(ReviewCreateCommand command);
    public List<ReviewWithAvgProjection> getAllReview(Long productId);
    public void deleteReview(Long reviewId);
    public void updateReview(ReviewUpdateCommand command);
}
