package com.gulbi.Backend.domain.rental.review.factory;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequestDto;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewFactory {
    private final UserService userService;
    private final ProductCrudService productCrudService;

    public Review createWithRegisterRequest(ReviewCreateRequestDto reviewCreateRequestDto, User user, Product product){
        String constent = reviewCreateRequestDto.getContent();
        Integer rating = reviewCreateRequestDto.getRating();
        return Review.builder().content(constent).rating(rating).user(user).product(product).build();
    }

    public Review createWithRegisterRequest(ReviewCreateRequestDto reviewCreateRequestDto) {
        Product product = productCrudService.getProductById(reviewCreateRequestDto.getProductId());
        return Review.builder()
                .content(reviewCreateRequestDto.getContent())
                .rating(reviewCreateRequestDto.getRating())
                .user(getAuthenticatedUser())
                .product(product)
                .build();
    }

    private User getAuthenticatedUser(){
        return userService.getAuthenticatedUser();
    }
}
