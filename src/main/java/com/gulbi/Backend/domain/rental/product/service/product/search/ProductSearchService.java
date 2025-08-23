package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.global.CursorPageable;

import java.util.List;

public interface ProductSearchService {
    public ProductOverviewSlice searchProductByQuery(ProductSearchRequest productSearchRequest, CursorPageable cursorPageable);
    public ProductDetailResponse getProductDetail(Long productId);
    public ProductOverviewSlice getMyProducts(CursorPageable cursorPageable);
    public ProductOverviewSlice getUserProducts(Long userId,CursorPageable cursorPageable);

}
