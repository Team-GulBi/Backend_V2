package com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;

import java.util.List;

public interface ProductSearchStrategy {
    public List<ProductOverViewResponse> search(String query);
}
