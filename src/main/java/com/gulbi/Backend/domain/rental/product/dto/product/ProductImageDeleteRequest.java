package com.gulbi.Backend.domain.rental.product.dto.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductImageDeleteRequest {
    private List<Long> imagesId;
}
