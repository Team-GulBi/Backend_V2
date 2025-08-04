package com.gulbi.Backend.domain.rental.product.service.product.update;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;

public interface ProductUpdatingService {
    public void updateProductViews(Long productId);
    public void updateProductInfo(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
}
