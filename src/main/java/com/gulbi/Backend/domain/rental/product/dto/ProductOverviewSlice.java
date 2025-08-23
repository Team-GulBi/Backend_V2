package com.gulbi.Backend.domain.rental.product.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ProductOverviewSlice {

	private final boolean hasNext;
	private final List<ProductOverViewResponse> products;

	public ProductOverviewSlice(boolean hasNext, List<ProductOverViewResponse> products) {
		this.hasNext = hasNext;
		this.products = products;
	}

}
