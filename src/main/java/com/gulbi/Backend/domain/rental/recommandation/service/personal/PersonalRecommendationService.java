package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gulbi.Backend.domain.rental.product.service.product.logging.LokiLoggingHandler;
import com.gulbi.Backend.domain.rental.recommandation.dto.PriorityCategoriesMap;
import com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy.RecommendationStrategyProviderService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import com.gulbi.Backend.global.CursorPageable;
import com.gulbi.Backend.global.PersonalCursorPageable;

import org.springframework.stereotype.Service;

@Service
public class PersonalRecommendationService implements PersonalProductProvider {
    private final LogQueryService logQueryService;
    private final QueryHandler queryHandler;

	public PersonalRecommendationService(LogQueryService logQueryService, QueryHandler queryHandler) {
		this.logQueryService = logQueryService;
		this.queryHandler = queryHandler;
	}

	@Override
    public PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalCursorPageable request) {
        //커서가 없으면 최초 요청임. 3개 => 7:3:2 비율로
        if(request.isEmptyCursor()){
            String result = logQueryService.getQueryOfMostViewedCategoriesByUser();
            PriorityCategoriesMap categoriesMap = queryHandler.getPriorityCategoriesMap(result);
            // 커서가 없으면 최초 요청이니 우선순위 대로 7 3 2 ??..
            //


        }
    }
}
