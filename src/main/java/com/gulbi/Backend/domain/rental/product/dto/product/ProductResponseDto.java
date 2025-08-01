package com.gulbi.Backend.domain.rental.product.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductResponseDto {
    private Long id;
    private String mainImage;
    private String title;
    private int price;
}