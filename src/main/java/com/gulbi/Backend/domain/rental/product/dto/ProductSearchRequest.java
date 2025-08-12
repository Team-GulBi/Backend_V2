package com.gulbi.Backend.domain.rental.product.dto;

import lombok.Getter;

@Getter
public class ProductSearchRequest {
    private final String Detail;
    private final String Query;

    private ProductSearchRequest(String detail, String query) {
        Detail = detail;
        Query = query;
    }

    public static ProductSearchRequest of(String detail, String query){
        return new ProductSearchRequest(detail, query);
    }
}

