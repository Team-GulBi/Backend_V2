package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.dto.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepoService categoryRepoService;
    @Override
    public CategoryBundle resolveCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId) {
        Category bCategory = categoryRepoService.findById(bCategoryId);
        Category mCategory = categoryRepoService.findById(mCategoryId);
        Category sCategory = categoryRepoService.findById(sCategoryId);
        return CategoryBundle.of(bCategory, mCategory, sCategory);
    }
    @Override
    public CategoryBundle resolveCategories(ProductCategoryUpdateRequest productCategoryUpdateRequest){
        Category bCategory = categoryRepoService.findById(productCategoryUpdateRequest.getBigCategoryId());
        Category mCategory = categoryRepoService.findById(productCategoryUpdateRequest.getMidCategoryId());
        Category sCategory = categoryRepoService.findById(productCategoryUpdateRequest.getSmallCategoryId());
        return CategoryBundle.of(bCategory, mCategory, sCategory);
    }
}
