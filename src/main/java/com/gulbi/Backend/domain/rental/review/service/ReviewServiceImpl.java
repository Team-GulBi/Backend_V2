package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.factory.ReviewFactory;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepoService reviewRepoService;
    private final UserService userService;
    private final ProductRepoService productRepoService;

    @Override
    public void addReviewToProduct(ReviewCreateCommand command) {
        Review review = createReviewWithUserAndProduct(command.getRequest());
        reviewRepoService.saveReview(review);
    }

    @Override
    public List<ReviewWithAvgProjection> getAllReview(Long productId) {
        return reviewRepoService.getReviewWithRateAvg(productId);
    }

    @Override
    public void deleteReview(Long reviewId){
        reviewRepoService.deleteReview(reviewId);
    }

    @Override
    public void updateReview(ReviewUpdateCommand command){
        reviewRepoService.updateReview();
    }


    private Review createReviewWithUserAndProduct(ReviewCreateRequest request) {
        User reviewer = getAuthenticatedUser();
        Product targetProduct = getProduct(request.getProductId());
        return ReviewFactory.createWithRegisterRequest(request,reviewer,targetProduct);
    }

    private User getAuthenticatedUser(){
        return userService.getAuthenticatedUser();
    }

    private Product getProduct(Long productId){
        return productRepoService.getProductById(productId);
    }

}
