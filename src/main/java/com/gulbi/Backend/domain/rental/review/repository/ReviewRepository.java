package com.gulbi.Backend.domain.rental.review.repository;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("SELECT r FROM Review r WHERE r.product.id = :productId")
    List<Review> findAllReviewByProductId(@Param("productId") Long productId);

    @Query("SELECT " +
            "r.id AS id, " +
            "r.content AS content, " +
            "r.rating AS rating, " +
            "u.nickname AS nickName, " +
            "(SELECT AVG(r.rating) FROM Review r WHERE r.product.id =:productId) AS averageRating " +
            "FROM Review r " +
            "JOIN User u ON u.id = r.user.id " +
            "JOIN Profile p ON u.id = p.user.id WHERE r.product.id =:productId")
    List<ReviewWithAvgProjection> findAllReviewWithRelationsByProductId(@Param("productId") Long productId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.id = :reviewId")
    void deleteReviewByReviewId(@Param("reviewId") Long reviewId);

    @Transactional
    @Modifying
    @Query("UPDATE Review r SET r.rating = :rating, r.content = :content WHERE r.id = :reviewId")
    void updateReviewInfo(@Param("rating") Integer rating, @Param("content")String content, @Param("reviewId") Long reviewId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.product = :product")
    void deleteAllReviewsByProduct(@Param("product")Product product);

}
