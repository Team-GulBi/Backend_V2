package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductContentUpdateCommand;

import java.util.List;

public interface ProductService {
    public List<ProductOverViewResponse> searchProductOverview(ProductSearchRequestDto productSearchRequestDto);
    public Long registrationProduct(ProductRegisterCommand command);
    public ProductDetailResponse getProductDetail(Long productId);
    public void updateProductViews(Long productId);
    public void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
    public void deleteProduct(Long productId);
}
