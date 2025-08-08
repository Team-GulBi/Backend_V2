package com.gulbi.Backend.domain.rental.review.factory;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequest;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

public class ReviewFactory {


    public static Review createWithRegisterRequest(ReviewCreateRequest reviewCreateRequest,User user, Product product) {
        return Review.builder()
                .content(reviewCreateRequest.getContent())
                .rating(reviewCreateRequest.getRating())
                .user(user)
                .product(product)
                .build();
    }

}
