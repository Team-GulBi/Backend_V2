package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.review.code.ReviewErrorCode;
import com.gulbi.Backend.domain.rental.review.dto.ReviewUpdateRequestDto;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.exception.ReviewException;
import com.gulbi.Backend.domain.rental.review.repository.ReviewRepository;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewCrudServiceImpl implements ReviewCrudService {

    private final ReviewRepository reviewRepository;
    private final ProductCrudService productCrudService;

    @Override
    public void saveReview(Review review) {
        try {
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            createDatabaseErrorException(review, this.getClass().getName(),e);
        } catch (IllegalArgumentException e) {
            createMissingReviewFieldException(review,this.getClass().getName(),e);
        }
    }

    @Override
    public List<ReviewWithAvgProjection> getReviewWithRateAvg(Long productId) {
        return reviewRepository.findAllReviewWithRelationsByProductId(productId);
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.deleteReviewByReviewId(reviewId);
    }

    @Override
    public void updateReview(ReviewUpdateRequestDto reviewUpdateRequestDto) {
        reviewRepository.updateReviewInfo(
                reviewUpdateRequestDto.getRating(),
                reviewUpdateRequestDto.getContent(),
                reviewUpdateRequestDto.getReviewId()
        );
    }

    @Override
    public void removeAllReviewsFromProductId(Long productId) {
        reviewRepository.deleteAllReviewsByProduct(resolveProduct(productId));
    }

    private Product resolveProduct(Long productId) {
        return productCrudService.getProductById(productId);
    }

    private void createDatabaseErrorException(Review review, String className, Throwable throwable) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                .args(review)
                .className(className)
                .stackTrace(throwable)
                .responseApiCode(ReviewErrorCode.DATABASE_ERROR)
                .build();
        throw new ReviewException.DatabaseErrorException(exceptionMetaData);
    }

    private void createMissingReviewFieldException(Review review, String className, Throwable throwable) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                .args(review)
                .className(className)
                .stackTrace(throwable)
                .responseApiCode(ReviewErrorCode.DATABASE_ERROR)
                .build();
        throw  new ReviewException.MissingReviewFiledException(exceptionMetaData);
    }
}
