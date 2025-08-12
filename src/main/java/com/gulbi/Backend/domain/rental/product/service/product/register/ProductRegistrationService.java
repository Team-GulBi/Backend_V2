package com.gulbi.Backend.domain.rental.product.service.product.register;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductRegisterCommand;

public interface ProductRegistrationService {
    public Long registerProduct(ProductRegisterCommand command);
}
