package com.gulbi.Backend.domain.rental.review.controller;

import com.gulbi.Backend.domain.rental.review.code.ReviewSuccessCode;
import com.gulbi.Backend.domain.rental.review.dto.ReviewCreateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequestDto;
import com.gulbi.Backend.domain.rental.review.service.ReviewService;
import com.gulbi.Backend.domain.user.response.SuccessCode;
import com.gulbi.Backend.global.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping
    @Operation(
            summary = "상품 리뷰 생성"
    )
    public ResponseEntity<RestApiResponse> createReview(@RequestBody @Validated ReviewCreateRequestDto request){
        reviewService.addReviewToProduct(request);
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

    @PatchMapping
    @Operation(
            summary = "리뷰수정"
    )
    public ResponseEntity<RestApiResponse> updateReview(@RequestBody ReviewUpdateRequestDto reviewUpdateRequestDto){
        reviewService.updateReview(reviewUpdateRequestDto);
        RestApiResponse response = new RestApiResponse(ReviewSuccessCode.REVIEW_INFO_UPDATED_SUCCESS);
        return ResponseEntity.ok(response);
    }
}
