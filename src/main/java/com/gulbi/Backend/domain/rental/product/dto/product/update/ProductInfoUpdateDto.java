package com.gulbi.Backend.domain.rental.product.dto.product.update;

import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductUpdateRequestDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductInfoUpdateDto {
    private final ProductUpdateRequestDto productUpdateRequestDto;
    private final ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto;
    private final Long productId;
    private ProductInfoUpdateDto(ProductUpdateRequestDto productUpdateRequestDto, ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto, Long productId){
        this.productUpdateRequestDto = productUpdateRequestDto;
        this.productCategoryUpdateRequestDto = productCategoryUpdateRequestDto;
        this.productId = productId;
    }
    public static ProductInfoUpdateDto of(ProductUpdateRequestDto productUpdateRequestDto,ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto,Long productId){
        return new ProductInfoUpdateDto(productUpdateRequestDto, productCategoryUpdateRequestDto,productId);
    }
}
