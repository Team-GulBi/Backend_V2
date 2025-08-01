package com.gulbi.Backend.domain.rental.product.factory;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryBusinessService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductFactory{
    private final UserService userService;
    private final CategoryBusinessService categoryBusinessService;
    public Product createWithRegisterRequestDto(ProductRegisterRequestDto productRegisterRequestDto) {
        User user = userService.getAuthenticatedUser();
        CategoryInProductDto categoryInProductDto = categoryBusinessService.resolveCategories(
                productRegisterRequestDto.getBcategoryId(),
                productRegisterRequestDto.getMcategoryId(),
                productRegisterRequestDto.getScategoryId()
        );
        return Product.builder()
                .user(user)
                .tag(productRegisterRequestDto.getTag())
                .title(productRegisterRequestDto.getTitle())
                .name(productRegisterRequestDto.getName())
                .price(productRegisterRequestDto.getPrice())
                .sido(productRegisterRequestDto.getSido())
                .sigungu(productRegisterRequestDto.getSigungu())
                .bname(productRegisterRequestDto.getBname())
                .description(productRegisterRequestDto.getDescription())
                .views(0)
                .bCategory(categoryInProductDto.getBCategory())
                .mCategory(categoryInProductDto.getMCategory())
                .sCategory(categoryInProductDto.getSCategory())
                .mainImage(productRegisterRequestDto.getMainImage().getImageUrl())
                .build();

    }
}
