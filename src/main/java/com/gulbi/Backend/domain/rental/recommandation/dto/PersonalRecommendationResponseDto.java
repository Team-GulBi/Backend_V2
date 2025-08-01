package com.gulbi.Backend.domain.rental.recommandation.dto;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import lombok.Getter;

import java.util.List;
@Getter
public class PersonalRecommendationResponseDto {
    private List<List<ProductOverViewResponse>> products;

    public PersonalRecommendationResponseDto(List<List<ProductOverViewResponse>> products) {
        this.products = products;
    }
    public PersonalRecommendationResponseDto(){}
}
