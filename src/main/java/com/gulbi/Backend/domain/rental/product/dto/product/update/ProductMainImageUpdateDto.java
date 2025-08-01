package com.gulbi.Backend.domain.rental.product.dto.product.update;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import lombok.Getter;

@Getter
public class ProductMainImageUpdateDto {
    private final Long productId;
    private final ImageUrl mainImageUrl;
    private ProductMainImageUpdateDto(Long productId, ImageUrl mainImageUrl){

        this.productId = productId;
        this.mainImageUrl = mainImageUrl;
    }
    public static ProductMainImageUpdateDto of(Long productId, ImageUrl mainImageUrl){
        return new ProductMainImageUpdateDto(productId, mainImageUrl);
    }
}
