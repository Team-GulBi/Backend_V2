package com.gulbi.Backend.domain.rental.product.service.product.register;

import com.gulbi.Backend.domain.rental.product.dto.product.request.register.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;

public interface ProductRegistrationService {
    public Long registerProduct(ProductRegisterRequestDto productRegisterRequestDto, NewProductImageRequest newProductImageRequest, ProductMainImageCreateRequestDto productMainImageCreateRequestDto);
}
