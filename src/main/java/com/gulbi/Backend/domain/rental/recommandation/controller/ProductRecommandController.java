package com.gulbi.Backend.domain.rental.recommandation.controller;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.recommandation.code.RecommendationSuccessCode;
import com.gulbi.Backend.domain.rental.recommandation.service.ProductRecommendFacade;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
import com.gulbi.Backend.global.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/recommand")
public class ProductRecommandController {
    private final ProductRecommendFacade productRecommendFacade;

    public ProductRecommandController(ProductRecommendFacade productRecommendFacade) {
        this.productRecommendFacade = productRecommendFacade;
    }

    @GetMapping("/realtime")
    public ResponseEntity<RestApiResponse> showRealTimePopularProduct(){
        List<ProductOverViewResponse> products = productRecommendFacade.getRealTimePopularProducts();
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.REALTIME_POPULAR_PRODUCTS_FOUND_SUCCESS,products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/recent")
    public ResponseEntity<RestApiResponse> showRecentRegistratedProducts(@Parameter(description = "lastCreatedAt",required = false)@RequestParam(value = "lastCreatedAt", required = false) LocalDateTime lastCreatedAt,
                                                                         @Parameter(description = "size", required = true) @RequestParam("size") int size){
        Pageable pageable = PageRequest.of(0, size);
        List<ProductOverViewResponse> products = productRecommendFacade.getRecentRegistrationProducts(pageable,lastCreatedAt);
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.LATEST_REGISTRATION_PRODUCTS_FOUND_SUCCESS,products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<RestApiResponse> showRecentProductByCategory(
            @Parameter(description = "대분류", required = true) @RequestParam("bCategoryId") Long bCategoryId,
            @Parameter(description = "중분류", required = false) @RequestParam("mCategoryId") Long mCategoryId,
            @Parameter(description = "소분류", required = false) @RequestParam("sCategoryId") Long sCategoryId,
            @Parameter(description = "lastCreatedAt", required = false) @RequestParam(value = "lastCreatedAt", required = false) LocalDateTime lastCreatedAt,
            @Parameter(description = "size", required = true) @RequestParam("size") int size
    ) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<ProductOverViewResponse> products = productRecommendFacade.getRecentProductByCategory(bCategoryId, mCategoryId,sCategoryId,lastCreatedAt,pageable);
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.PRODUCTS_FOUND_BY_CATEGORIES_SUCCESS,products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/personality")
    public ResponseEntity<RestApiResponse> getPersonalRecommendation(@ModelAttribute PersonalRecommendationRequestDto request) {
        return buildResponse(request);
    }

    @PostMapping("/personality")
    public ResponseEntity<RestApiResponse> postPersonalRecommendation(@RequestBody PersonalRecommendationRequestDto request) {
        return buildResponse(request);
    }

    private ResponseEntity<RestApiResponse> buildResponse(PersonalRecommendationRequestDto request) {
        PersonalRecommendationResponseDto products = productRecommendFacade.getPersonalizedRecommendationProducts(request);
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.PERSONAL_RECOMMENDATION_PRODUCTS_FOUND_SUCCESS, products);
        return ResponseEntity.ok(response);
    }
}

