package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;

public interface PersonalProductProvider {
    PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto);
}
