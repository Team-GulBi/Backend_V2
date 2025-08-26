package com.gulbi.Backend.domain.rental.recommandation.controller;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewPrioritySlices;
import com.gulbi.Backend.domain.rental.recommandation.code.RecommendationSuccessCode;
import com.gulbi.Backend.domain.rental.recommandation.service.ProductRecommend;
import com.gulbi.Backend.global.PersonalCursorRequest;
import com.gulbi.Backend.global.PersonalCursorPageable;
import com.gulbi.Backend.global.response.RestApiResponse;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recommand")
public class ProductRecommandController {
    private final ProductRecommend productRecommend;

	public ProductRecommandController(ProductRecommend productRecommend) {
		this.productRecommend = productRecommend;
	}

	@GetMapping("/realtime")
    public ResponseEntity<RestApiResponse> showRealTimePopularProduct(){
        List<ProductOverViewResponse> products = productRecommend.getRealTimePopularProducts();
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.REALTIME_POPULAR_PRODUCTS_FOUND_SUCCESS,products);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/personality")
    public ResponseEntity<RestApiResponse> postPersonalRecommendation(
        @RequestBody(required = false) List<PersonalCursorRequest> request,
        @ParameterObject Pageable pageable) {
        PersonalCursorPageable cursor = new PersonalCursorPageable(request, pageable);
		ProductOverviewPrioritySlices data =productRecommend.getPersonalizedRecommendationProducts(cursor);
        RestApiResponse response = new RestApiResponse(RecommendationSuccessCode.PERSONAL_RECOMMENDATION_PRODUCTS_FOUND_SUCCESS,data);
        return ResponseEntity.ok(response);
    }


}

