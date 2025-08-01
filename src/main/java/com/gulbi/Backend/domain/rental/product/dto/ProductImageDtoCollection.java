package com.gulbi.Backend.domain.rental.product.dto;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;
@Getter
public class ProductImageDtoCollection {
    private final List<ProductImageDto> productImages;

    public ProductImageDtoCollection(List<ProductImageDto> productImages) {
        this.productImages = productImages;
    }

    public static ProductImageDtoCollection of(List<ProductImageDto> images){
        return new ProductImageDtoCollection(images);
    }

    public List<String> toUrlList() {
        return productImages.stream()
                .map(ProductImageDto::getUrl)
                .collect(Collectors.toList());
    }
}
