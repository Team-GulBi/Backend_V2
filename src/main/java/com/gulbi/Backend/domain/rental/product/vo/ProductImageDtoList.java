package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductImageResponse;

import java.util.List;
import java.util.stream.Collectors;

public class ProductImageDtoList {
    private final List<ProductImageResponse> productImages;

    public ProductImageDtoList(List<ProductImageResponse> productImages) {
        this.productImages = productImages;
    }

    public static ProductImageDtoList of(List<ProductImageResponse> images){
        return new ProductImageDtoList(images);
    }

    public List<String> toUrlList() {
        return productImages.stream()
                .map(ProductImageResponse::getUrl)
                .collect(Collectors.toList());
    }
}
