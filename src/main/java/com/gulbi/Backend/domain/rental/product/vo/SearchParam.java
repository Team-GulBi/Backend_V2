package com.gulbi.Backend.domain.rental.product.vo;

import lombok.Getter;

@Getter
public class SearchParam {
    private final String query;
    private final String detail;

    private SearchParam(String query, String detail) {
        this.query = query;
        this.detail = detail;
    }

    public static SearchParam of(String query, String detail){
        return new SearchParam(query, detail);
    }

}
