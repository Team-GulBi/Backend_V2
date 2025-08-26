package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchCondition;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.global.CursorPageable;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepoService {
    Product save(Product product);
    Product findById(Long productId);
    Product findByIdWithUser(Long productId);
    ProductOverviewSlice findOverViewByTitle(ProductSearchCondition title, CursorPageable pageable);
    ProductOverviewSlice findOverViewByUser(ProductSearchCondition user, CursorPageable pageable);
    ProductOverviewSlice findOverViewByCategoryPair(ProductSearchCondition categoryPair, CursorPageable pageable);
    List<ProductOverViewResponse> findOverViewByproductIds(List<Long> productIds);
    List<ProductOverViewResponse> findOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt);
    List<ProductOverViewResponse> findOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable);
    void delete(Long productId);

}
