package com.gulbi.Backend.domain.rental.product.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductOverviewPrioritySlices {
	private final List<ProductOverviewPrioritySlice> slices=new ArrayList<>();

	public void add(ProductOverviewPrioritySlice slice){
		slices.add(slice);
	}

}
