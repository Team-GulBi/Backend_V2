package com.gulbi.Backend.domain.rental.recommandation.service.query;

import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;

public interface QueryHandler {

    ExtractedProductIds getListOfProductIds(String queryResult);
    ExtractedRecommendation getMapOfRecommandation(String queryResult);
}
