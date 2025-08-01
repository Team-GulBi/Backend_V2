package com.gulbi.Backend.domain.rental.product.service.product.register;

import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;

public interface ProductRegistrationService {
    public Long registerProduct(ProductRegisterRequestDto productRegisterRequestDto, ProductImageCreateRequestDto productImageCreateRequestDto, ProductMainImageCreateRequestDto productMainImageCreateRequestDto);
}
