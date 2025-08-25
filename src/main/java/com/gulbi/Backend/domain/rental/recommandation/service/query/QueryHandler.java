package com.gulbi.Backend.domain.rental.recommandation.service.query;

import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesMap;
import com.gulbi.Backend.domain.rental.recommandation.vo.PopularProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesQueue;

public interface QueryHandler {

    PopularProductIds extractProductIdsFromQueryResult(String queryResult);
    PriorityCategoriesQueue getPriorityCategoriesMap(String queryResult);
}
