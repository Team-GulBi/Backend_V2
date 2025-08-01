package com.gulbi.Backend.domain.rental.product.service.product.logging;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductDto;

public interface ProductLogHandler {
    void loggingQueryData(String query, String detail);
    void loggingProductIdData(Long productId);
    void loggingReturnedProductData(ProductDto productDto);
}
