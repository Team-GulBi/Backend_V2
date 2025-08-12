package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryProjection;
import com.gulbi.Backend.domain.rental.product.entity.Category;

import java.util.List;

public interface CategoryRepoService {
    public List<CategoryProjection> findAllBigCategories();
    public List<CategoryProjection> findAllBelowByParentId(Long categoryId);
    public Category findById(Long categoryId);
}
