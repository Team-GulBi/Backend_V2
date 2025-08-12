package com.gulbi.Backend.domain.rental.product.factory;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductRegisterRequest;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory{
    public static Product createWithRegisterRequestDto(User user, CategoryBundle categories, ProductRegisterRequest productRegisterRequest) {
        return Product.builder()
                .user(user)
                .title(productRegisterRequest.getTitle())
                .name(productRegisterRequest.getName())
                .price(productRegisterRequest.getPrice())
                .sido(productRegisterRequest.getSido())
                .sigungu(productRegisterRequest.getSigungu())
                .bname(productRegisterRequest.getBname())
                .description(productRegisterRequest.getDescription())
                .views(0)
                .bCategory(categories.getBCategory())
                .mCategory(categories.getMCategory())
                .sCategory(categories.getSCategory())
                .build();

    }
}
