package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;

public interface ImageService {
    public void updateProductImages(ProductImageUpdateCommand productImageUpdateCommand);

}
