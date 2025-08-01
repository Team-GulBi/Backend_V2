package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.*;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.response.ProductDetailResponseDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductInfoUpdateDto;

import java.util.List;

public interface ProductService {
    public List<ProductOverViewResponse> searchProductOverview(ProductSearchRequestDto productSearchRequestDto);
    public Long registrationProduct(ProductRegisterRequestDto productRegisterRequestDto, ProductImageCreateRequestDto productImageCreateRequestDto, ProductMainImageCreateRequestDto productMainImageCreateRequestDto);
    public ProductDetailResponseDto getProductDetail(Long productId);
    public void updateProductViews(Long productId);
    public void updateProduct(ProductInfoUpdateDto productInfoUpdateDto, ProductImageInfoUpdateDto productImageInfoUpdateDto);
    public void deleteProduct(Long productId);
}
