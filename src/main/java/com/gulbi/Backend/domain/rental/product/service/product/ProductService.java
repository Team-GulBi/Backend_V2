package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.*;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.response.ProductDetailResponseDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;

import java.util.List;

public interface ProductService {
    public List<ProductOverViewResponse> searchProductOverview(ProductSearchRequestDto productSearchRequestDto);
    public Long registrationProduct(ProductRegisterRequestDto productRegisterRequestDto, NewProductImageRequest newProductImageRequest, ProductMainImageCreateRequestDto productMainImageCreateRequestDto);
    public ProductDetailResponseDto getProductDetail(Long productId);
    public void updateProductViews(Long productId);
    public void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
    public void deleteProduct(Long productId);
}
