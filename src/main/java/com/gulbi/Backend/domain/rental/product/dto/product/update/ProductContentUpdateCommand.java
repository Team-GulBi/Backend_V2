package com.gulbi.Backend.domain.rental.product.dto.product.update;

import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.productTextUpdateRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductContentUpdateCommand {
    private final productTextUpdateRequest productTextUpdateRequest;
    private final ProductCategoryUpdateRequest productCategoryUpdateRequest;
    private final Long productId;
    private ProductContentUpdateCommand(productTextUpdateRequest productTextUpdateRequest, ProductCategoryUpdateRequest productCategoryUpdateRequest, Long productId){
        this.productTextUpdateRequest = productTextUpdateRequest;
        this.productCategoryUpdateRequest = productCategoryUpdateRequest;
        this.productId = productId;
    }
    public static ProductContentUpdateCommand of(productTextUpdateRequest productTextUpdateRequest,
        ProductCategoryUpdateRequest productCategoryUpdateRequest,Long productId){
        return new ProductContentUpdateCommand(productTextUpdateRequest, productCategoryUpdateRequest,productId);
    }
}
