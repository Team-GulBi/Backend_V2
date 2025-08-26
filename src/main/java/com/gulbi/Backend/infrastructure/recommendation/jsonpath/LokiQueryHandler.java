package com.gulbi.Backend.infrastructure.recommendation.jsonpath;

import static com.gulbi.Backend.infrastructure.recommendation.jsonpath.JsonPathQuery.*;

import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.vo.PopularProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesQueue;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LokiQueryHandler implements QueryHandler {
    private final String className = this.getClass().getName();

    @Override
    public PopularProductIds extractProductIdsFromQueryResult(String queryResult) {
        // 추출된 JSON 문자열에서 ProductIds만 추출
        List<String> productIds = JsonPath.read(queryResult, EXTRACT_PRODUCT_IDS.getQuery());
        return PopularProductIds.fromStrings(productIds);
    }


    @Override
    public PriorityCategoriesQueue getPriorityCategoriesMap(String queryResult) {
        List<String> countList = JsonPath.read(queryResult, EXTRACT_TOTAL_RESULT_COUNT.getQuery());
        int count = countList.size() - 1; // 마지막 인덱스부터 시작
        PriorityCategoriesQueue priorityCategoriesQueue = new PriorityCategoriesQueue();

        for (; count >= 0; count--) { // 0까지 감소
            String testQuery = String.format(EXTRACT_BIG_CATEGORY_ID.getQuery(), count);
            String testQuery2 = String.format(EXTRACT_MIDDLE_CATEGORY_ID.getQuery(), count);
            String testQuery3 = String.format(EXTRACT_TOTAL_VIEWED.getQuery(), count,1);

            String bigCategoryId = JsonPath.read(queryResult,testQuery);
            String midCategoryId = JsonPath.read(queryResult,testQuery2);
            String priority = JsonPath.read(queryResult,testQuery3);

            PriorityCategoriesQueue.CategoryPair categoryPair = new PriorityCategoriesQueue.CategoryPair(bigCategoryId,midCategoryId,priority);
            priorityCategoriesQueue.add(categoryPair);
        }
        return priorityCategoriesQueue;

    }

}
