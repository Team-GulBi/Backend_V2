package com.gulbi.Backend.domain.rental.product.dto;

import lombok.Getter;

@Getter
public class ProductImageResponse {
    private final Long id;
    private final String url;
    private final boolean main;

    // Product 객체에서 필요한 필드를 가져오는 생성자
    public ProductImageResponse(Long id, String url, boolean main) {
        this.id = id;
        this.url = url;
        this.main = main;
    }
}