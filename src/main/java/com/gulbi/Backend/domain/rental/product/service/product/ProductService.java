package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.global.CursorPageable;

import java.util.List;

public interface ProductService {
    Long registrationProduct(ProductRegisterCommand command);

    ProductOverviewSlice searchProductOverview(ProductSearchRequest productSearchRequest, CursorPageable cursorPageable);
    ProductDetailResponse getProductDetail(Long productId);
    ProductOverviewSlice getAllUserProducts(Long userId,CursorPageable cursorPageable);
    ProductOverviewSlice getAllProducts(CursorPageable cursorPageable);


    void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
    void deleteProduct(Long productId);
}
