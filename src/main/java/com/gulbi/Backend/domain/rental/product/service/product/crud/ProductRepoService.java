package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.productTextUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRepoService {
    Long saveProduct(Product product);
    ProductDto getProductDtoById(Long productId);
    Product getProductById(Long productId);
    List<ProductOverViewResponse> getProductOverViewByTag(String tag, String tag2, String tag3);
    List<ProductOverViewResponse> getProductOverViewByTitle(String title);
    List<ProductOverViewResponse> getProductOverViewByproductIds(List<Long> productIds);
    List<ProductOverViewResponse> getProductOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt);
    List<ProductOverViewResponse> getProductOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable);
    void updateProductViews(Long productId);
    void updateProductInfo(productTextUpdateRequest productTextUpdateRequest);
    void updateProductMainImage(ProductMainImageUpdateDto productMainImageUpdateDto);
    void deleteProduct(Long productId);
    void updateProductTextOnly(ProductContentUpdateCommand dto);
    void updateProductCategories(ProductContentUpdateCommand dto);

}
