package com.gulbi.Backend.domain.rental.product.dto.product.request.update;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProductCategoryUpdateRequest {
    private final Long bCategoryId;
    private final Long mCategoryId;
    private final Long sCategoryId;
    @Setter
    private Long productId;

    private ProductCategoryUpdateRequest(Long bCategoryId, Long mCategoryId, Long sCategoryId){
        this.bCategoryId = bCategoryId;
        this.mCategoryId = mCategoryId;
        this.sCategoryId = sCategoryId;
    }

    public static ProductCategoryUpdateRequest of(Long bCategoryId, Long mCategoryId, Long sCategoryId){
        return new ProductCategoryUpdateRequest(bCategoryId, mCategoryId, sCategoryId);
    }
}
