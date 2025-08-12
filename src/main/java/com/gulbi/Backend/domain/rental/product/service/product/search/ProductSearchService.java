package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;

import java.util.List;

public interface ProductSearchService {
    public List<ProductOverViewResponse> searchProductByQuery(ProductSearchRequest productSearchRequest);
    public ProductDetailResponse getProductDetail(Long productId);
}
