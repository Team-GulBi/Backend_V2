package com.gulbi.Backend.domain.rental.product.repository;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import jakarta.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 제목 검색
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE p.title LIKE CONCAT('%', :query, '%')")
    List<ProductOverViewResponse> findAllByTitle(@Param("query") String query);

    // 태그 검색
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE (:tag1 IS NULL OR p.tag LIKE CONCAT('%', :tag1, '%')) " +
        "AND (:tag2 IS NULL OR p.tag LIKE CONCAT('%', :tag2, '%')) " +
        "AND (:tag3 IS NULL OR p.tag LIKE CONCAT('%', :tag3, '%'))")
    List<ProductOverViewResponse> findAllByTag(@Param("tag1") String tagQuery1,
        @Param("tag2") String tagQuery2,
        @Param("tag3") String tagQuery3);

    // ID 리스트 조회
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE p.id IN :productIds")
    List<ProductOverViewResponse> findAllOverViewByIdIn(@Param("productIds") List<Long> productIds);

    // 생성일 기준 조회
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE (:lastCreatedAt IS NULL OR p.createdAt < :lastCreatedAt) " +
        "ORDER BY p.createdAt DESC")
    List<ProductOverViewResponse> findAllOverviewByCreatedAtDesc(@Param("lastCreatedAt") LocalDateTime lastCreatedAt,
        Pageable pageable);

    // 카테고리 조회
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE (:bCategoryId IS NULL OR :bCategoryId = 0 OR p.bCategory.id = :bCategoryId) " +
        "AND (:mCategoryId IS NULL OR :mCategoryId = 0 OR p.mCategory.id = :mCategoryId) " +
        "AND (:sCategoryId IS NULL OR :sCategoryId = 0 OR p.sCategory.id = :sCategoryId) " +
        "AND (:lastCreatedAt IS NULL OR p.createdAt < :lastCreatedAt)")
    List<ProductOverViewResponse> findAllByCategoryIds(@Param("bCategoryId") Long bCategoryId,
        @Param("mCategoryId") Long mCategoryId,
        @Param("sCategoryId") Long sCategoryId,
        @Param("lastCreatedAt") LocalDateTime lastCreatedAt,
        Pageable pageable);

    Optional<Product> findProductById(@Param("id") Long id);

    @Query("SELECT p FROM Product p " +
        "LEFT JOIN FETCH p.user " +
        "JOIN FETCH p.bCategory " +
        "JOIN FETCH p.mCategory " +
        "JOIN FETCH p.sCategory " +
        "WHERE p.id = :id")
    Optional<Product> findByIdWithAll(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId")
    void deleteAllById(@Param("productId") Long productId);
}
