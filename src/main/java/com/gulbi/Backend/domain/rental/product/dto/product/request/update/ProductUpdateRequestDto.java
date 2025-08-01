package com.gulbi.Backend.domain.rental.product.dto.product.request.update;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
public class ProductUpdateRequestDto {
    @Schema(example = "수정1,수정2")
    private final String tag;
    @Schema(example = "제목수정")
    private final String title;
    @Schema(example = "이름수정")
    private final String name;
    @Schema(example = "500000")
    private final Integer price;
    @Schema(example = "시도수정")
    private final String sido;
    @Schema(example = "시군구수정")
    private final String sigungu;
    @Schema(example = "동수정")
    private final String bname;
    @Schema(example = "설명수정")
    private final String description;

    @Hidden
    @Setter
    private CategoryInProductDto categoryInProduct;

    @Hidden
    @Setter
    private Long productId;

    private ProductUpdateRequestDto(String tag, String title, String productName, Integer price, String sido, String sigungu, String bname, String description) {
        this.tag = tag;
        this.title = title;
        this.name = productName;
        this.price = price;
        this.sido = sido;
        this.sigungu = sigungu;
        this.bname = bname;
        this.description = description;
    }

    public static ProductUpdateRequestDto of(String tag, String title, String productName, Integer price, String sido, String sigungu, String bname, String description) {
        return new ProductUpdateRequestDto(tag, title, productName, price, sido, sigungu, bname, description);
    }

    public static ProductUpdateRequestDto of(){
        return new ProductUpdateRequestDto(null, null, null, null, null, null, null, null);
    }
}
