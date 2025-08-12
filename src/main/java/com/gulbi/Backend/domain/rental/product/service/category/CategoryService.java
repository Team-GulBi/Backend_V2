package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductCategoryUpdateRequest;

public interface CategoryService {
    public CategoryBundle resolveCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId);
    public CategoryBundle resolveCategories(ProductCategoryUpdateRequest productCategoryUpdateRequest);
}
