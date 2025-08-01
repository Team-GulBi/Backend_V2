package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.user.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReviewService {
    public void addReviewToProduct(ReviewCreateRequestDto request);
    public List<ReviewWithAvgProjection> getAllReview(Long productId);
    public void deleteReview(Long reviewId);
    public void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto);
}
