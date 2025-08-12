package com.gulbi.Backend.domain.rental.product.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ProductTextUpdateRequest {
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


    private ProductTextUpdateRequest(String tag, String title, String productName, Integer price, String sido, String sigungu, String bname, String description) {
        this.tag = tag;
        this.title = title;
        this.name = productName;
        this.price = price;
        this.sido = sido;
        this.sigungu = sigungu;
        this.bname = bname;
        this.description = description;
    }

    public static ProductTextUpdateRequest of(String tag, String title, String productName, Integer price, String sido, String sigungu, String bname, String description) {
        return new ProductTextUpdateRequest(tag, title, productName, price, sido, sigungu, bname, description);
    }

    public static ProductTextUpdateRequest of(){
        return new ProductTextUpdateRequest(null, null, null, null, null, null, null, null);
    }
}
