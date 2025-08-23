package com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.global.CursorPageable;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("제목")
@RequiredArgsConstructor
public class ProductSearchByTitleStrategy implements ProductSearchStrategy {
    private final ProductRepoService productRepoService;

	@Override
	public ProductOverviewSlice search(String query, CursorPageable cursorPageable) {
		return productRepoService.findOverViewByTitle(query, cursorPageable);
	}
}
