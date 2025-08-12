package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.code.ReviewErrorCode;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvg;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.exception.ReviewException;
import com.gulbi.Backend.domain.rental.review.repository.ReviewRepository;
import com.gulbi.Backend.global.error.DatabaseException;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.InfraErrorCode;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewRepoJpaService implements ReviewRepoService {

    private final String className = this.getClass().getName();

    private final ReviewRepository reviewRepository;
    private final ProductRepoService productRepoService;

    @Override
    public Review findById(Long reviewId) {
        try {
            return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException(
                    ExceptionMetaDataFactory.of(reviewId, className, null, ReviewErrorCode.REVIEW_NOT_FOUND)
                ));
        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(reviewId, className, e, InfraErrorCode.DB_EXCEPTION));
        }
    }


    @Override
    public void save(Review review) {
        try {
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(review, className, e, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public List<ReviewWithAvg> findAllByProductIdWithAvg(Long productId) {
        try {
            List<ReviewWithAvg> list = reviewRepository.findAllByProductIdWithAvg(productId);
            if (list.isEmpty()) {
                throw new ReviewException(
                    ExceptionMetaDataFactory.of(productId, className, null, ReviewErrorCode.REVIEW_NOT_FOUND));
            }
            return list;
        }catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, e, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public void delete(Long reviewId) {
        try {
            reviewRepository.deleteById(reviewId);
        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new DatabaseException(
                ExceptionMetaDataFactory.of(reviewId, className, e, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public void deleteAllByProductId(Long productId) {
        try {
            reviewRepository.deleteAllByProduct(productId);
        }catch (DataIntegrityViolationException | JpaSystemException | PersistenceException e) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, e, InfraErrorCode.DB_EXCEPTION));
        }

    }

}
