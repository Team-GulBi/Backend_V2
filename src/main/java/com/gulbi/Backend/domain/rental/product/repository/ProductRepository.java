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
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query(value = "SELECT p.id AS id, p.main_image AS mainImage, p.title AS title, p.price AS price FROM products p WHERE p.title LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<ProductOverViewResponse> findProductsByTitle(@Param("query")String query);


    @Query(value = "SELECT p.id AS id, p.main_image AS mainImage, p.title AS title, p.price AS price " +
            "FROM Product p " +
            "WHERE " +
            "p.tag LIKE CONCAT('%', :query1, '%')" +
            "AND" +
            "(:query2 IS NULL OR p.tag LIKE CONCAT('%', :query2, '%'))" +
            "AND" +
            "(:query3 IS NULL OR p.tag LIKE CONCAT('%', :query3, '%') ) ", nativeQuery = true)
    List<ProductOverViewResponse> findProductsByTag(@Param("query1") String tagQuery1, @Param("query2") String tagQuery2, @Param("query3") String tagQuery3);

    @Query(value = "SELECT p.id AS id, p.mainImage AS mainImage, p.title AS title, p.price AS price "+
                    "FROM Product p"+
                    " WHERE p.id IN :productIds")
    List<ProductOverViewResponse> findProductsByIds(@Param("productIds") List<Long> productIds);

    @Query("SELECT p.id AS id, p.mainImage AS mainImage, p.title AS title, p.price AS price " +
            "FROM Product p " +
            "WHERE (:lastCreatedAt IS NULL OR p.createdAt < :lastCreatedAt) " +
            "ORDER BY p.createdAt DESC")
    List<ProductOverViewResponse> findAllProductOverviewsByCreatedAtDesc(@Param("lastCreatedAt") LocalDateTime lastCreatedAt, Pageable pageable);


    @Query(value = "SELECT p FROM Product p WHERE " +
            "(:bCategoryId IS NULL OR :bCategoryId = 0 OR p.bCategory.id = :bCategoryId) AND " +
            "(:mCategoryId IS NULL OR :mCategoryId = 0 OR p.mCategory.id = :mCategoryId) AND " +
            "(:sCategoryId IS NULL OR :sCategoryId = 0 OR p.sCategory.id = :sCategoryId) AND " +
            "(:lastCreatedAt IS NULL OR p.createdAt < :lastCreatedAt)")
    List<ProductOverViewResponse> findAllProductByCategoryIds(
            @Param("bCategoryId") Long bCategoryId,
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

    @Query("SELECT p FROM Product p " +
        "LEFT JOIN FETCH p.contractTemplate " +
        "WHERE p.id = :productId")
    Optional<Product> findByIdWithTemplate(@Param("productId") Long productId);


    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id=:productId")
    void deleteAllbyId(@Param("productId") Long productId);
}
