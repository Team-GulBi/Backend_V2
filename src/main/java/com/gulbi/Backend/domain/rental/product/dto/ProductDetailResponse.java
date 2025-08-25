package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.Images;
import com.gulbi.Backend.domain.rental.review.dto.ReviewsWithAvg;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ProductDetailResponse {
    private final String tag;
    private final String title;
    private final String productName;
    private final String price;
    private final String view;
    private final String sido;
    private final String sigungu;
    private final String bname;
    private final String description;
    private final LocalDateTime created_at;

    private final CategoriesResponse productCategories;

    private final ProductImagesResponse productImages;

    private final ReviewsWithAvg reviews;
    //우선 유저 이름만, 확장 시 별도 dto로 감싸기
    private final String userNickname;

    private final boolean owner;


    public static ProductDetailResponse of(Product product, Images images, ReviewsWithAvg reviews, boolean isOwner) {
        CategoriesResponse categoriesResponse = CategoriesResponse.of(product);
        return ProductDetailResponse.builder()
                .tag(product.getTag())
                .title(product.getTitle())
                .productName(product.getName())
                .price(String.valueOf(product.getPrice()))
                .view(String.valueOf(product.getViews()))
                .sido(product.getSido())
                .sigungu(product.getSigungu())
                .bname(product.getBname())
                .description(product.getDescription())
                .productCategories(categoriesResponse)
                .created_at(LocalDateTime.now())
                .productImages(ProductImagesResponse.of(images))
                .reviews(reviews)
                .userNickname(product.getUser().getNickname())
                .build();
    }
}
