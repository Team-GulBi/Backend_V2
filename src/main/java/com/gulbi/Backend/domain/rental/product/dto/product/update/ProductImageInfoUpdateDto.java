package com.gulbi.Backend.domain.rental.product.dto.product.update;

import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductImageDeleteRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductExistingMainImageUpdateRequestDto;
import lombok.Getter;

@Getter
public class ProductImageInfoUpdateDto {

    private final ProductImageCreateRequestDto toBeAddedImages; // 추가되는 상품 사진
    private final ProductImageCreateRequestDto toBeUpdatedMainImageFile; // 교체할 메인 이미지 파일
    private final ProductExistingMainImageUpdateRequestDto toBeUpdatedMainImageWithUrl; // 교체할 메인 이미지 URL 정보
    private final ProductImageDeleteRequestDto toBeDeletedImages; // 삭제할 이미지 ID 리스트
    private final Long productId;

    private ProductImageInfoUpdateDto(ProductImageCreateRequestDto toBeAddedImages, ProductImageCreateRequestDto toBeUpdatedMainImageFile, ProductExistingMainImageUpdateRequestDto toBeUpdatedMainImageWithUrl, ProductImageDeleteRequestDto toBeDeletedImages, Long productId){
        this.toBeAddedImages = toBeAddedImages;
        this. toBeUpdatedMainImageFile = toBeUpdatedMainImageFile;
        this.toBeUpdatedMainImageWithUrl=toBeUpdatedMainImageWithUrl;
        this.toBeDeletedImages = toBeDeletedImages;
        this.productId = productId;
    }

    public static ProductImageInfoUpdateDto of(ProductImageCreateRequestDto toBeAddedImages, ProductImageCreateRequestDto toBeUpdatedMainImageFile, ProductExistingMainImageUpdateRequestDto toBeUpdatedMainImageWithUrl, ProductImageDeleteRequestDto toBeDeletedImages, Long productId) {
        return new ProductImageInfoUpdateDto(toBeAddedImages, toBeUpdatedMainImageFile, toBeUpdatedMainImageWithUrl, toBeDeletedImages,productId);
    }

}
