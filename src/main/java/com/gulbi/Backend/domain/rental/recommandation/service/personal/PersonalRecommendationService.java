package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesMap;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesQueue;
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
			// 요청을 보낸 유저의 관심사 카테고리 쌍을 3개를 뽑는 쿼리(추후 더 확장이 가능 함)
            String result = logQueryService.getQueryOfMostViewedCategoriesByUser();
			// 해당 쌍을 가공 후 맵으로 만듬.
			// 맵이지만 pop을 할때 queue처럼 써야함.
            PriorityCategoriesQueue priorityCategoriesQueue = queryHandler.getPriorityCategoriesMap(result);

			//pageable의 size체크 priority size 체크 6 3 1 비율로,


        }
    }
}
