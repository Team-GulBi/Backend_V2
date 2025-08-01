package com.gulbi.Backend.domain.rental.product.dto.product.request.update;

import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductExistingMainImageUpdateRequestDto {
    @Schema(description = "메인 이미지 URL", example = "https://yajoba.s3.ap-northeast-2.amazonaws.com/images/b45dce5c-31f3-4ebf-a1eb-e6c77ae8a846_chill2.jpeg", type = "string")
    private ImageUrl mainImageUrl;


    private ProductExistingMainImageUpdateRequestDto() {}

    private ProductExistingMainImageUpdateRequestDto(String mainImageUrl) {
        this.mainImageUrl = ImageUrl.of(mainImageUrl);
    }

    public static ProductExistingMainImageUpdateRequestDto of(String mainImageUrl) {
        return new ProductExistingMainImageUpdateRequestDto(mainImageUrl);
    }
}
