package com.gulbi.Backend.domain.rental.recommandation.service;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewPrioritySlices;
import com.gulbi.Backend.global.PersonalCursorPageable;

import java.util.List;

public interface ProductRecommend {
    List<ProductOverViewResponse> getRealTimePopularProducts();
    ProductOverviewPrioritySlices getPersonalizedRecommendationProducts(PersonalCursorPageable request);

}
