package com.gulbi.Backend.domain.rental.recommandation.service.query;

import com.gulbi.Backend.domain.rental.recommandation.dto.PriorityCategoriesMap;
import com.gulbi.Backend.domain.rental.recommandation.vo.PopularProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;

public interface QueryHandler {

    PopularProductIds extractProductIdsFromQueryResult(String queryResult);
    PriorityCategoriesMap getPriorityCategoriesMap(String queryResult);
}
