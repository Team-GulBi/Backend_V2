package com.gulbi.Backend.domain.rental.product.service.category;

import com.gulbi.Backend.domain.rental.product.code.CategoryErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.category.CategoryProjection;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import com.gulbi.Backend.domain.rental.product.exception.CategoryException;
import com.gulbi.Backend.domain.rental.product.repository.CategoryRepository;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryRepoJpaService implements CategoryRepoService {

    private final CategoryRepository categoryRepository;
    private static final String CLASS_NAME = CategoryRepoJpaService.class.getName();

    @Override
    public List<CategoryProjection> findAllBigCategories() {
        return requireNonEmpty(
            categoryRepository.findAllNoParentProjection(),
            null
        );
    }

    @Override
    public List<CategoryProjection> findAllBelowByParentId(Long categoryId) {
        return requireNonEmpty(
            categoryRepository.findBelowCategory(categoryId),
            categoryId
        );
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryException(
                ExceptionMetaDataFactory.of(categoryId, CLASS_NAME, null, CategoryErrorCode.NOT_FOUND_CATEGORY)
            ));
    }

    private <T> List<T> requireNonEmpty(List<T> list, Object arg) {
        if (list.isEmpty()) {
            throw new CategoryException(
                ExceptionMetaDataFactory.of(arg, CLASS_NAME, null, CategoryErrorCode.NOT_FOUND_CATEGORY)
            );
        }
        return list;
    }
}
