package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.code.CategoryErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.category.CategoryProjection;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import com.gulbi.Backend.domain.rental.product.exception.CategoryException;
import com.gulbi.Backend.domain.rental.product.repository.CategoryRepository;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryRepoJpaService implements CategoryRepoService {

    private final CategoryRepository categoryRepository;
    private final String className = this.getClass().getName();
    @Override
    public List<CategoryProjection> getBigCategories() {
        List<CategoryProjection> bigCategoryList = categoryRepository.findAllNoParentProjection();
        if (bigCategoryList.isEmpty()) {
            throwNotInitializedCategoryException();
        }
        return bigCategoryList;
    }

    @Override
    public List<CategoryProjection> getBelowCategoriesByParentId(Long categoryId) {
        List<CategoryProjection> belowCategoryList = categoryRepository.findBelowCategory(categoryId);
        if (belowCategoryList.isEmpty()) {
            throwCategoryException(categoryId);
        }
        return belowCategoryList;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(Math.toIntExact(categoryId))
                .orElseThrow(() -> throwCategoryException(categoryId));
    }

    private CategoryException.NotInitializedCategoryException throwNotInitializedCategoryException() {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .className(className)
                .responseApiCode(CategoryErrorCode.NOT_INITIALIZED_CATEGORIES)
                .build();
        throw new CategoryException.NotInitializedCategoryException(exceptionMetaData);
    }


    private CategoryException.CategoryNotFoundException throwCategoryException(Object fieldValue) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(fieldValue)
                .className(className)
                .responseApiCode(CategoryErrorCode.NOT_FOUND_CATEGORY)
                .build();
        throw new CategoryException.CategoryNotFoundException(exceptionMetaData);
    }
}
