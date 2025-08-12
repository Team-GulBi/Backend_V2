package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductDetailResponse;

import java.util.List;

public interface ProductSearchService {
    public List<ProductOverViewResponse> searchProductByQuery(ProductSearchRequestDto productSearchRequestDto);
    public ProductDetailResponse getProductDetail(Long productId);
}
