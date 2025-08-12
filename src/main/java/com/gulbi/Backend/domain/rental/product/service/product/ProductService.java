package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;

import java.util.List;

public interface ProductService {
    public List<ProductOverViewResponse> searchProductOverview(ProductSearchRequest productSearchRequest);
    public Long registrationProduct(ProductRegisterCommand command);
    public ProductDetailResponse getProductDetail(Long productId);
    public void updateProductViews(Long productId);
    public void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
    public void deleteProduct(Long productId);
}
