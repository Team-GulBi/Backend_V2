package com.gulbi.Backend.domain.rental.product.dto.product.request;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
@Getter
@RequiredArgsConstructor
public class ProductImageDeleteRequestDto {
    private List<Long> imagesId;
}
