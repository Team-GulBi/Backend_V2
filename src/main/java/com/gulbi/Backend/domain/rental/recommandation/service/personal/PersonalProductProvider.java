package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewPrioritySlices;
import com.gulbi.Backend.global.PersonalCursorPageable;

public interface PersonalProductProvider {
    ProductOverviewPrioritySlices getPersonalizedRecommendationProducts(PersonalCursorPageable request);
}
