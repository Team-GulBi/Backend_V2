package com.gulbi.Backend.domain.rental.product.dto;

import lombok.Getter;

@Getter
public class ProductSearchRequestDto {
    private final String Detail;
    private final String Query;

    private ProductSearchRequestDto(String detail, String query) {
        Detail = detail;
        Query = query;
    }

    public static ProductSearchRequestDto of(String detail, String query){
        return new ProductSearchRequestDto(detail, query);
    }
}

