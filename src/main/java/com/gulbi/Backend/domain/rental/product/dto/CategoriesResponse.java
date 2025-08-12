package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.rental.product.entity.Product;

import lombok.Getter;

@Getter
public class CategoriesResponse {
	private final Long bigCategoryId;
	private final String bigName;
	private final Long midCategoryId;
	private final String midName;
	private final Long smallCategoryId;
	private final String smallName;

	public CategoriesResponse(Long bigCategoryId, String bigName, Long midCategoryId, String midName,
		Long smallCategoryId,
		String smallName) {
		this.bigCategoryId = bigCategoryId;
		this.bigName = bigName;
		this.midCategoryId = midCategoryId;
		this.midName = midName;
		this.smallCategoryId = smallCategoryId;
		this.smallName = smallName;
	}

	public static CategoriesResponse of(Product product){
		return new CategoriesResponse(product.getBCategory().getId(), product.getBCategory().getName(), product.getMCategory().getId(), product.getMCategory().getName(), product.getSCategory().getId(), product.getSCategory().getName());
	}
}
