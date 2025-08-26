package com.gulbi.Backend.domain.rental.recommandation.service.realtime;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PopularRecommendationService implements PopularProductProvider {
    private final LogQueryService logQueryService;
    private final QueryHandler queryHandler;
    private final ProductRepoService productRepoService;

    public PopularRecommendationService(LogQueryService logQueryService, ProductRepoService productRepoService, QueryHandler queryHandler) {
        this.logQueryService = logQueryService;
        this.productRepoService = productRepoService;
        this.queryHandler = queryHandler;
    }
    @Override
    public List<ProductOverViewResponse> getRealTimePopularProducts() {
        // 최근 600분동안 로그에 집계된 많이 조회된 20개의 상품 아이디 반환
        String response = logQueryService.getQueryOfPopularProductIds();
        // 쿼리 결과에서 원하는 정보를 추출해주는 랜들러 호출
        List<Long> productIds = queryHandler.extractProductIdsFromQueryResult(response).getProductIds();
        return productRepoService.findOverViewByproductIds(productIds);
    }
}
