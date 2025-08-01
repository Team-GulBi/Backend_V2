package com.gulbi.Backend.domain.rental.product.dto.product.request.update;

import lombok.Getter;

@Getter
public class ProductCategoryUpdateRequestDto {
    private final Long bCategoryId;
    private final Long mCategoryId;
    private final Long sCategoryId;

    private ProductCategoryUpdateRequestDto(Long bCategoryId, Long mCategoryId, Long sCategoryId){
        this.bCategoryId = bCategoryId;
        this.mCategoryId = mCategoryId;
        this.sCategoryId = sCategoryId;
    }

    public static ProductCategoryUpdateRequestDto of(Long bCategoryId, Long mCategoryId, Long sCategoryId){
        return new ProductCategoryUpdateRequestDto(bCategoryId, mCategoryId, sCategoryId);
    }
}
