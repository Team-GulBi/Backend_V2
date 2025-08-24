package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import com.gulbi.Backend.global.PersonalCursorPageable;

public interface PersonalProductProvider {
    PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalCursorPageable request);
}
