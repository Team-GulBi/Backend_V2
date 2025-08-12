package com.gulbi.Backend.domain.rental.product.dto.product;

import lombok.Getter;

@Getter
public class ProductImageUpdateCommand {

    private final NewProductImageRequest toBeAddedImages; // 추가 할 상품 사진
    private final NewProductImageRequest toBeUpdatedMainImageFile; // 교체할 메인 이미지 파일
    private final MainImageUrlUpdateRequest toBeUpdatedMainImageWithUrl; // 교체할 메인 이미지 URL 정보
    private final ProductImageDeleteRequest toBeDeletedImages; // 삭제할 이미지 ID 리스트
    private final Long productId;

    private ProductImageUpdateCommand(
        NewProductImageRequest toBeAddedImages, NewProductImageRequest toBeUpdatedMainImageFile, MainImageUrlUpdateRequest toBeUpdatedMainImageWithUrl, ProductImageDeleteRequest toBeDeletedImages, Long productId){
        this.toBeAddedImages = toBeAddedImages;
        this. toBeUpdatedMainImageFile = toBeUpdatedMainImageFile;
        this.toBeUpdatedMainImageWithUrl=toBeUpdatedMainImageWithUrl;
        this.toBeDeletedImages = toBeDeletedImages;
        this.productId = productId;
    }

    public static ProductImageUpdateCommand of(
        NewProductImageRequest toBeAddedImages, NewProductImageRequest toBeUpdatedMainImageFile, MainImageUrlUpdateRequest toBeUpdatedMainImageWithUrl, ProductImageDeleteRequest toBeDeletedImages, Long productId) {
        return new ProductImageUpdateCommand(toBeAddedImages, toBeUpdatedMainImageFile, toBeUpdatedMainImageWithUrl, toBeDeletedImages,productId);
    }

}
