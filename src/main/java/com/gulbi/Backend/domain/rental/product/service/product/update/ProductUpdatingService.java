package com.gulbi.Backend.domain.rental.product.service.product.update;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductInfoUpdateDto;

public interface ProductUpdatingService {
    public void updateProductViews(Long productId);
    public void updateProductInfo(ProductInfoUpdateDto productInfoUpdateDto, ProductImageInfoUpdateDto productImageInfoUpdateDto);
}
