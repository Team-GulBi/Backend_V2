package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBusinessServiceImpl implements CategoryBusinessService{
    private final CategoryRepoService categoryRepoService;
    @Override
    public CategoryInProductDto resolveCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId) {
        Category bCategory = categoryRepoService.getCategoryById(bCategoryId);
        Category mCategory = categoryRepoService.getCategoryById(mCategoryId);
        Category sCategory = categoryRepoService.getCategoryById(sCategoryId);
        return CategoryInProductDto.of(bCategory, mCategory, sCategory);
    }
    @Override
    public CategoryInProductDto resolveCategories(ProductCategoryUpdateRequest productCategoryUpdateRequest){
        Category bCategory = categoryRepoService.getCategoryById(productCategoryUpdateRequest.getBCategoryId());
        Category mCategory = categoryRepoService.getCategoryById(productCategoryUpdateRequest.getMCategoryId());
        Category sCategory = categoryRepoService.getCategoryById(productCategoryUpdateRequest.getSCategoryId());
        return CategoryInProductDto.of(bCategory, mCategory, sCategory);
    }
}
