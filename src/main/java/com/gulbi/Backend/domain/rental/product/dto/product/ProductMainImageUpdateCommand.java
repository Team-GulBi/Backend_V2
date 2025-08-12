package com.gulbi.Backend.domain.rental.product.dto.product;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import lombok.Getter;

@Getter
public class ProductMainImageUpdateCommand {
    private final Long productId;
    private final ImageUrl mainImageUrl;
    private ProductMainImageUpdateCommand(Long productId, ImageUrl mainImageUrl){

        this.productId = productId;
        this.mainImageUrl = mainImageUrl;
    }
    public static ProductMainImageUpdateCommand of(Long productId, ImageUrl mainImageUrl){
        return new ProductMainImageUpdateCommand(productId, mainImageUrl);
    }
}
