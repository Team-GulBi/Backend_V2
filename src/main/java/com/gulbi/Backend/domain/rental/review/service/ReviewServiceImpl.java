package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.factory.ReviewFactory;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewCrudService reviewCrudService;
    private final ReviewFactory reviewFactory;

    @Override
    public void addReviewToProduct(ReviewCreateRequestDto review) {
        reviewCrudService.saveReview(createReviewWithUserAndProduct(review));
    }

    @Override
    public List<ReviewWithAvgProjection> getAllReview(Long productId) {
        return reviewCrudService.getReviewWithRateAvg(productId);
    }

    @Override
    public void deleteReview(Long reviewId){
        reviewCrudService.deleteReview(reviewId);
    }

    @Override
    public void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto){
        reviewCrudService.updateReview(reviewUpdateRequestDto);
    }


    private Review createReviewWithUserAndProduct(ReviewCreateRequestDto review) {
        return reviewFactory.createWithRegisterRequest(review);
    }



}
