package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.response.ProductDetailResponseDto;

import java.util.List;

public interface ProductSearchService {
    public List<ProductOverViewResponse> searchProductByQuery(ProductSearchRequestDto productSearchRequestDto);
    public ProductDetailResponseDto getProductDetail(Long productId);
}
