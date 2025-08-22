package com.gulbi.Backend.domain.rental.review.service;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvg;
import com.gulbi.Backend.domain.rental.review.entity.Review;

import java.util.List;

public interface ReviewRepoService {
    Review findById(Long reviewId);
    void save(Review review);
    List<ReviewWithAvg> findAllByProductIdWithAvg(Long productId);
    void delete(Long reviewId);
    void deleteAllByProduct(Product product);
}
