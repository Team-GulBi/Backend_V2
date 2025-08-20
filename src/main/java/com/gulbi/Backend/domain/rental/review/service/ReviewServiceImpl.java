package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvg;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.exception.ReviewException;
import com.gulbi.Backend.domain.rental.review.factory.ReviewFactory;
import com.gulbi.Backend.domain.user.entity.User;

import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{
    private final ReviewRepoService reviewRepoService;
    private final UserService userService;
    private final ProductRepoService productRepoService;

    @Override
    public void addReviewToProduct(ReviewCreateCommand command) {
        Review review = createReviewWithUserAndProduct(command);
        reviewRepoService.save(review);
    }

    @Override
    public List<ReviewWithAvg> getAllReview(Long productId) {
        try {
            return reviewRepoService.findAllByProductIdWithAvg(productId);
        }catch (ReviewException e){
            return Collections.emptyList();
        }

    }

    @Override
    public void deleteReview(Long reviewId){
        reviewRepoService.delete(reviewId);
    }

    @Override
    public void updateReview(ReviewUpdateCommand command){
        Review review = reviewRepoService.findById(command.getReviewId());
        review.update(command.getRequest().getContent(), command.getRequest().getRating());
        reviewRepoService.save(review);
    }


    private Review createReviewWithUserAndProduct(ReviewCreateCommand command) {
        User reviewer = getAuthenticatedUser();
        Product targetProduct = getProduct(command.getProductId());
        return ReviewFactory.createWithRegisterRequest(command.getRequest(),reviewer,targetProduct);
    }

    private User getAuthenticatedUser(){
        return userService.getAuthenticatedUser();
    }

    private Product getProduct(Long productId){
        return productRepoService.findProductById(productId);
    }

}
