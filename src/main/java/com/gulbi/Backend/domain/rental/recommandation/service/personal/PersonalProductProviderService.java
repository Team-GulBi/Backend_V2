package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy.RecommendationStrategyProviderService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.ProductLogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import org.springframework.stereotype.Service;

@Service
public class PersonalProductProviderService implements PersonalProductProvider {
    private final ProductLogQueryService productLogQueryService;
    private final QueryHandler queryHandler;
    private final RecommendationStrategyProviderService recommendationStrategyProviderService;

    public PersonalProductProviderService(ProductLogQueryService productLogQueryService, QueryHandler queryHandler, RecommendationStrategyProviderService recommendationStrategyProviderService) {
        this.productLogQueryService = productLogQueryService;
        this.queryHandler = queryHandler;
        this.recommendationStrategyProviderService = recommendationStrategyProviderService;
    }
    @Override
    public PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto) {
        if(!personalRecommendationRequestDto.isPagination()) {
            String result = productLogQueryService.getQueryOfMostViewedCategoriesByUser();
            ExtractedRecommendation extractedRecommendation = queryHandler.getMapOfRecommandation(result);
            extractedRecommendation.printRecommendationIndices();
            return recommendationStrategyProviderService.getRecommendatedProducts(extractedRecommendation,personalRecommendationRequestDto);
        }
            return recommendationStrategyProviderService.getRecommendatedProducts(personalRecommendationRequestDto);
    }
}
