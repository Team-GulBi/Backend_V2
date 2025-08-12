package com.gulbi.Backend.domain.rental.product.dto.product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductContentUpdateCommand {
    private final ProductTextUpdateRequest productTextUpdateRequest;
    private final ProductCategoryUpdateRequest productCategoryUpdateRequest;
    private final Long productId;
    private ProductContentUpdateCommand(ProductTextUpdateRequest productTextUpdateRequest, ProductCategoryUpdateRequest productCategoryUpdateRequest, Long productId){
        this.productTextUpdateRequest = productTextUpdateRequest;
        this.productCategoryUpdateRequest = productCategoryUpdateRequest;
        this.productId = productId;
    }
    public static ProductContentUpdateCommand of(ProductTextUpdateRequest productTextUpdateRequest,
        ProductCategoryUpdateRequest productCategoryUpdateRequest,Long productId){
        return new ProductContentUpdateCommand(productTextUpdateRequest, productCategoryUpdateRequest,productId);
    }
}
