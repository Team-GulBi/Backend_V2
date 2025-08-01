package com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy;

import com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search.ProductSearchStrategy;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecommendationStrategyProviderService implements RecommendationStrategyProvider {
    private final Map<String, CategoryBasedRecommendStrategy> recommendStrategyServiceMap;

    @Autowired
    public RecommendationStrategyProviderService(Map<String, CategoryBasedRecommendStrategy> recommendStrategyServiceMap, Map<String, ProductSearchStrategy> productSearchStrategies) {
        this.recommendStrategyServiceMap = recommendStrategyServiceMap;
    }

    @Override
    public PersonalRecommendationResponseDto getRecommendatedProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto) {
        return recommendStrategyServiceMap.get("latestProductQueryStrategy").getRecommendatedProducts(personalRecommendationRequestDto);
    }

    @Override
    public PersonalRecommendationResponseDto getRecommendatedProducts(ExtractedRecommendation extractedRecommendation, PersonalRecommendationRequestDto personalRecommendationRequestDto) {
        return recommendStrategyServiceMap.get("latestProductQueryStrategy").getRecommendatedProducts(extractedRecommendation);
    }


}
