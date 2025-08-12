package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.rental.product.entity.Category;
import lombok.Getter;

@Getter
public class CategoryBundle {
    private final Category bCategory;
    private final Category mCategory;
    private final Category sCategory;

    private CategoryBundle(Category bCategory, Category mCategory, Category sCategory){
        this.bCategory = bCategory;
        this.mCategory = mCategory;
        this.sCategory = sCategory;
    }

    public static CategoryBundle of(Category bCategory, Category mCategory, Category sCategory){
        return new CategoryBundle(bCategory, mCategory, sCategory);
    }
}
