package com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service("태그")
@RequiredArgsConstructor
public class ProductSearchByTagStrategy implements ProductSearchStrategy {

    private final ProductRepository productRepository;


    @Override
    public List<ProductOverViewResponse> search(String query) {
        // 불변 리스트가 아닌, 가변 리스트로 변환
        List<String> queries = new ArrayList<>(Arrays.stream(query.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())  // 빈 문자열을 제거
                .toList());

        // 태그가 3개 미만일 경우 null을 추가
        while (queries.size() < 3) {
            queries.add(null);  // 기본값으로 null을 추가
        }

        return productRepository.findProductsByTag(queries.get(0), queries.get(1), queries.get(2));
    }


}
