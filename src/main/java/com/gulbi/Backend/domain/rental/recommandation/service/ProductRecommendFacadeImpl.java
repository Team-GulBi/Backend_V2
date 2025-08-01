package com.gulbi.Backend.domain.rental.recommandation.service;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.recommandation.service.personal.PersonalProductProvider;
import com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy.RecommendationStrategyProviderService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.ProductLogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.service.realtime.PopularProductProvider;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductRecommendFacadeImpl implements ProductRecommendFacade {
    private final ProductCrudService productCrudService;
    private final PersonalProductProvider personalProductProvider;
    private final PopularProductProvider popularProductProvider;

    public ProductRecommendFacadeImpl(ProductLogQueryService productLogQueryService, ProductCrudService productCrudService, QueryHandler queryHandler, RecommendationStrategyProviderService recommendatedProviderService, PersonalProductProvider personalProductProvider, PopularProductProvider popularProductProvider) {
        this.productCrudService = productCrudService;
        this.personalProductProvider = personalProductProvider;
        this.popularProductProvider = popularProductProvider;
    }

    @Override
    public List<ProductOverViewResponse> getRealTimePopularProducts() {
        return popularProductProvider.getRealTimePopularProducts();
    }

    @Override
    public List<ProductOverViewResponse> getRecentRegistrationProducts(Pageable pageable, LocalDateTime lastCreatedAt) {
        return productCrudService.getProductOverViewByCreatedAtDesc(pageable,lastCreatedAt);
    }

    @Override
    public List<ProductOverViewResponse> getRecentProductByCategory(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable) {
        return productCrudService.getProductOverViewByCategories(bCategoryId, mCategoryId, sCategoryId,lastCreatedAt,pageable);
    }

    @Override
    public PersonalRecommendationResponseDto getPersonalizedRecommendationProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto) {
        return personalProductProvider.getPersonalizedRecommendationProducts(personalRecommendationRequestDto);
    }
}
