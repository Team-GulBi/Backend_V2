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


    // ID 리스트 조회
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE p.id IN :productIds")
    List<ProductOverViewResponse> findAllOverViewByIdIn(@Param("productIds") List<Long> productIds);

    // 생성일 기준 조회
    // ToDo: QueryDSL로 변경예정, 삭제예정
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse(" +
        "p.id, p.mainImage, p.title, p.price, p.createdAt) " +
        "FROM Product p " +
        "WHERE (:lastCreatedAt IS NULL OR p.createdAt < :lastCreatedAt) " +
        "ORDER BY p.createdAt DESC")
    List<ProductOverViewResponse> findAllOverviewByCreatedAtDesc(@Param("lastCreatedAt") LocalDateTime lastCreatedAt,
        Pageable pageable);

    // ToDo: QueryDSL로 변경예정, 삭제예정
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

    @Query("SELECT p FROM Product p WHERE p.deletedAt IS NULL AND p.id= :id")
    Optional<Product> findById(@Param("id") Long id);

    @Query("SELECT p FROM Product p " +
        "LEFT JOIN FETCH p.user " +
        "JOIN FETCH p.bCategory " +
        "JOIN FETCH p.mCategory " +
        "JOIN FETCH p.sCategory " +
        "WHERE p.id = :id AND p.deletedAt IS NULL")
    Optional<Product> findByIdWithAll(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id = :productId")
    void deleteAllById(@Param("productId") Long productId);
}
