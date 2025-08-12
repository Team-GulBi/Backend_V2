package com.gulbi.Backend.domain.rental.product.dto.product;

import lombok.Getter;

import java.util.*;

import com.gulbi.Backend.domain.rental.product.vo.image.Images;

@Getter
public class ProductImagesResponse {
    private final List<ProductImageResponse> productImages;

    public ProductImagesResponse(List<ProductImageResponse> productImages) {
        this.productImages = productImages;
    }

    public static ProductImagesResponse of(List<ProductImageResponse> images){
        return new ProductImagesResponse(images);
    }
    public static ProductImagesResponse of(Images images){
        List<ProductImageResponse> list = images.getImages().stream()
            .map(image -> new ProductImageResponse(image.getId(), image.getUrl(), image.getMain()))
            .toList();
        return ProductImagesResponse.of(list);
    }


}
