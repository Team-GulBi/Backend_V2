package com.gulbi.Backend.domain.rental.review.controller;

import com.gulbi.Backend.domain.rental.review.code.ReviewSuccessCode;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequest;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateCommand;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequest;
import com.gulbi.Backend.domain.rental.review.service.ReviewService;
import com.gulbi.Backend.global.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping("/product/{productId}")
    @Operation(
            summary = "상품 리뷰 생성"
    )
    public ResponseEntity<RestApiResponse> createReview(@RequestBody @Validated ReviewCreateRequest request,
    @PathVariable("productId") Long productId){
        ReviewCreateCommand command = new ReviewCreateCommand(request, productId);
        reviewService.addReviewToProduct(command);
        RestApiResponse response = new RestApiResponse(ReviewSuccessCode.REVIEW_REGISTER_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(
            summary = "리뷰 삭제",
            description = "지우고자 하는 리뷰Id를 넣어주세요"
    )
    public ResponseEntity<RestApiResponse> deleteReview(@PathVariable("reviewId") Long reviewId){
        reviewService.deleteReview(reviewId);
        RestApiResponse response = new RestApiResponse(ReviewSuccessCode.REVIEW_DELETED_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{reviewId}")
    @Operation(
            summary = "리뷰수정"
    )
    public ResponseEntity<RestApiResponse> updateReview(@RequestBody ReviewUpdateRequest request,
        @PathVariable("reviewId")Long reviewId){

        ReviewUpdateCommand command = new ReviewUpdateCommand(request, reviewId);
        reviewService.updateReview(command);
        RestApiResponse response = new RestApiResponse(ReviewSuccessCode.REVIEW_INFO_UPDATED_SUCCESS);
        return ResponseEntity.ok(response);
    }
}
