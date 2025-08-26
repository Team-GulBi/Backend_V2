package com.gulbi.Backend.domain.rental.product.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductOverviewPrioritySlice {
	private final boolean hasNext;
	private final String nickname;
	private final Long bCategoryId;
	private final Long mCategoryId;
	private final int priority;
	private final List<ProductOverViewResponse> products;

	public ProductOverviewPrioritySlice(boolean hasNext, String nickname, Long bCategoryId, Long mCategoryId,
		int priority, List<ProductOverViewResponse> products) {
		this.hasNext = hasNext;
		this.nickname = nickname;
		this.bCategoryId = bCategoryId;
		this.mCategoryId = mCategoryId;
		this.priority = priority;
		this.products = products;
	}
}
