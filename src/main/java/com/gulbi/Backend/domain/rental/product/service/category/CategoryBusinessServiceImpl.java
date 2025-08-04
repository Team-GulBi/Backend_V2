package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryBusinessServiceImpl implements CategoryBusinessService{
    private final CategoryCrudService categoryCrudService;
    @Override
    public CategoryInProductDto resolveCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId) {
        Category bCategory = categoryCrudService.getCategoryById(bCategoryId);
        Category mCategory = categoryCrudService.getCategoryById(mCategoryId);
        Category sCategory = categoryCrudService.getCategoryById(sCategoryId);
        return CategoryInProductDto.of(bCategory, mCategory, sCategory);
    }
    @Override
    public CategoryInProductDto resolveCategories(ProductCategoryUpdateRequest productCategoryUpdateRequest){
        Category bCategory = categoryCrudService.getCategoryById(productCategoryUpdateRequest.getBCategoryId());
        Category mCategory = categoryCrudService.getCategoryById(productCategoryUpdateRequest.getMCategoryId());
        Category sCategory = categoryCrudService.getCategoryById(productCategoryUpdateRequest.getSCategoryId());
        return CategoryInProductDto.of(bCategory, mCategory, sCategory);
    }
}
