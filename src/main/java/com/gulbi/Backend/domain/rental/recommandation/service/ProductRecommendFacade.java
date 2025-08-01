package com.gulbi.Backend.domain.rental.recommandation.service;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductRecommendFacade {
    List<ProductOverViewResponse> getRealTimePopularProducts();
    List<ProductOverViewResponse> getRecentRegistrationProducts(Pageable pageable, LocalDateTime lastCreatedAt);
    List<ProductOverViewResponse> getRecentProductByCategory(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable);
    PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto);

}
