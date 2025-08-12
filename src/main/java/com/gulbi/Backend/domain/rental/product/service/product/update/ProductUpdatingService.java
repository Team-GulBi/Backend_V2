package com.gulbi.Backend.domain.rental.product.service.product.update;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;

public interface ProductUpdatingService {
    public void updateProductInfo(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand);
}
