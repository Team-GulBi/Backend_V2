package com.gulbi.Backend.domain.rental.recommandation.service.query;

import static com.gulbi.Backend.domain.rental.recommandation.service.query.JsonPathQuery.*;

import com.gulbi.Backend.domain.rental.recommandation.code.JsonPathErrorCode;
import com.gulbi.Backend.domain.rental.recommandation.dto.CategoryPair;
import com.gulbi.Backend.domain.rental.recommandation.dto.PriorityCategoriesMap;
import com.gulbi.Backend.domain.rental.recommandation.exception.JsonPathException;
import com.gulbi.Backend.domain.rental.recommandation.vo.PopularProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LokiQueryHandler implements QueryHandler{
    private final String className = this.getClass().getName();

    @Override
    public PopularProductIds extractProductIdsFromQueryResult(String queryResult) {
        // 추출된 JSON 문자열에서 ProductIds만 추출
        List<String> productIds = JsonPath.read(queryResult, EXTRACT_PRODUCT_IDS.getQuery());
        return PopularProductIds.fromStrings(productIds);
    }


    @Override
    public PriorityCategoriesMap getPriorityCategoriesMap(String queryResult) {
        List<String> countList = JsonPath.read(queryResult, TESt_QUERY.getQuery());
        int count = countList.size() - 1; // 마지막 인덱스부터 시작
        PriorityCategoriesMap map = new PriorityCategoriesMap();

        for (; count >= 0; count--) { // 0까지 감소
            String testQuery = String.format(JsonPathQuery.TEST_QUERY_2.getQuery(), count);
            String testQuery2 = String.format(JsonPathQuery.TEST_QUERY_3.getQuery(), count);
            String testQuery3 = String.format(JsonPathQuery.TEST_QUERY_4.getQuery(), count,1);

            String bigCategoryId = JsonPath.read(queryResult,testQuery);
            String midCategoryId = JsonPath.read(queryResult,testQuery2);
            String priority = JsonPath.read(queryResult,testQuery3);

            CategoryPair categories = new CategoryPair(Long.parseLong(bigCategoryId),Long.parseLong(midCategoryId));
            map.put(priority,categories);
        }
        return map;

    }

}
