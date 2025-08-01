package com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("제목")
@RequiredArgsConstructor
public class ProductSearchByTitleStrategy implements ProductSearchStrategy {
    private final ProductRepository productRepository;

    @Override
    public List<ProductOverViewResponse> search(String query) {
        return productRepository.findProductsByTitle(query);
    }
}
