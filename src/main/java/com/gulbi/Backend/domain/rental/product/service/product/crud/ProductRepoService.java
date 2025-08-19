package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepoService {
    Product save(Product product);
    Product findProductById(Long productId);
    Product findProductByIdWithUser(Long productId);
    List<ProductOverViewResponse> findProductOverViewByTag(String tag, String tag2, String tag3);
    List<ProductOverViewResponse> findProductOverViewByTitle(String title);
    List<ProductOverViewResponse> findProductOverViewByproductIds(List<Long> productIds);
    List<ProductOverViewResponse> findProductOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt);
    List<ProductOverViewResponse> findProductOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable);
    void delete(Long productId);

}
