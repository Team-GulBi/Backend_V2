package com.gulbi.Backend.domain.rental.review.repository;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvg;
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

    @Query("SELECT " +
            "r.id AS id, " +
            "r.content AS content, " +
            "r.rating AS rating, " +
            "u.nickname AS nickName, " +
            "(SELECT AVG(r.rating) FROM Review r WHERE r.product.id =:productId) AS averageRating " +
            "FROM Review r " +
            "JOIN User u ON u.id = r.user.id " +
            "WHERE r.product.id =:productId")
    List<ReviewWithAvg> findAllByProductIdWithAvg(@Param("productId") Long productId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Review r WHERE r.id = :reviewId")
    void deleteById(@Param("reviewId") Long reviewId);


    @Transactional
    @Modifying
    void deleteAllByProduct(Product product);

}
