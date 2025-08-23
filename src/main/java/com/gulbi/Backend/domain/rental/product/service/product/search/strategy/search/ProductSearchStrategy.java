package com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.global.CursorPageable;

import java.util.List;

public interface ProductSearchStrategy {
    public ProductOverviewSlice search(String query, CursorPageable cursorPageable);
}
