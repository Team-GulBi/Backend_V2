package com.gulbi.Backend.domain.rental.product.dto;

import lombok.Getter;

@Getter
// @Setter
public class ProductCategoryUpdateRequest {
    // @JsonProperty("bigCategoryId")
    private final Long bigCategoryId;

    // @JsonProperty("midCategoryId")
    private final Long midCategoryId;

    // @JsonProperty("smallCategoryId")
    private final Long smallCategoryId;


    public ProductCategoryUpdateRequest(Long bigCategoryId, Long midCategoryId, Long smallCategoryId){
        this.bigCategoryId = bigCategoryId;
        this.midCategoryId = midCategoryId;
        this.smallCategoryId = smallCategoryId;
    }

    public static ProductCategoryUpdateRequest of(Long bigCategoryId, Long midCategoryId, Long smallCategoryId){
        return new ProductCategoryUpdateRequest(bigCategoryId, midCategoryId, smallCategoryId);
    }
}
