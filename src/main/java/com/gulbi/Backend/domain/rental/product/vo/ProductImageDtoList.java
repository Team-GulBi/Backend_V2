package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductImageDtoList {
    private final List<ProductImageDto> productImages;

    public ProductImageDtoList(List<ProductImageDto> productImages) {
        this.productImages = productImages;
    }

    public static ProductImageDtoList of(List<ProductImageDto> images){
        return new ProductImageDtoList(images);
    }

    public List<String> toUrlList() {
        return productImages.stream()
                .map(ProductImageDto::getUrl)
                .collect(Collectors.toList());
    }
}
