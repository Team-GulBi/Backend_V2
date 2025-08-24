package com.gulbi.Backend.domain.rental.recommandation.service;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.recommandation.service.personal.PersonalProductProvider;
import com.gulbi.Backend.domain.rental.recommandation.service.realtime.PopularProductProvider;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import com.gulbi.Backend.global.PersonalCursorPageable;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductRecommendationService implements ProductRecommend {
    private final PersonalProductProvider personalProductProvider;
    private final PopularProductProvider popularProductProvider;

	public ProductRecommendationService(PersonalProductProvider personalProductProvider,
		PopularProductProvider popularProductProvider) {
		this.personalProductProvider = personalProductProvider;
		this.popularProductProvider = popularProductProvider;
	}

	@Override
    public List<ProductOverViewResponse> getRealTimePopularProducts() {
        return popularProductProvider.getRealTimePopularProducts();
    }


    @Override
    public PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalCursorPageable request) {
        return personalProductProvider.getPersonalizedRecommendationProducts(request);
    }
}
