package com.gulbi.Backend.domain.rental.product.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverviewSlice {
	private String nickname;
	private boolean hasNext;
	private List<ProductOverViewResponse> products;

public ProductOverviewSlice(boolean hasNext, List<ProductOverViewResponse> products){
	this.hasNext = hasNext;
	this.products = products;
	this.nickname = null;
}
}
