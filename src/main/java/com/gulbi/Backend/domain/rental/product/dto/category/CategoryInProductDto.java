package com.gulbi.Backend.domain.rental.product.dto.category;

import com.gulbi.Backend.domain.rental.product.entity.Category;
import lombok.Getter;

@Getter
public class CategoryInProductDto {
    private final Category bCategory;
    private final Category mCategory;
    private final Category sCategory;

    private CategoryInProductDto(Category bCategory, Category mCategory, Category sCategory){
        this.bCategory = bCategory;
        this.mCategory = mCategory;
        this.sCategory = sCategory;
    }

    public static CategoryInProductDto of(Category bCategory, Category mCategory, Category sCategory){
        return new CategoryInProductDto(bCategory, mCategory, sCategory);
    }
}
