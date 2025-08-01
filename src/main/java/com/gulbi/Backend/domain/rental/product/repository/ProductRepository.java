package com.gulbi.Backend.domain.rental.product.repository;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
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



    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.product.ProductDto(p.id, p.tag, p.title, p.name, p.views, p.price, p.sido, p.sigungu, p.bname, p.description, p.bCategory, p.mCategory, p.sCategory,p.user, p.createdAt) " +
            "FROM Product p WHERE p.id = :id")
    Optional<ProductDto> findProductDtoById(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Optional<Product> findProductById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.views = p.views + 1 WHERE p.id = :id ")
    Integer updateProductViews(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.bCategory = :bCategory, " +
            "p.mCategory = :mCategory, " +
            "p.sCategory = :sCategory " +
            "WHERE p.id = :productId")
    void updateProductCategories(@Param("productId") Long productId,
                                 @Param("bCategory") Category bCategory,
                                 @Param("mCategory") Category mCategory,
                                 @Param("sCategory") Category sCategory);
    @Transactional
    @Modifying
    @Query("UPDATE Product p " +
            "SET p.tag = COALESCE(:tag, p.tag), " +
            "p.title = COALESCE(:title, p.title), " +
            "p.name = COALESCE(:name, p.name), " +
            "p.price = COALESCE(:price, p.price), " +
            "p.sido = COALESCE(:sido, p.sido), " +
            "p.sigungu = COALESCE(:sigungu, p.sigungu), " +
            "p.bname = COALESCE(:bname, p.bname), " +
            "p.description = COALESCE(:description, p.description) " +
            "WHERE p.id = :productId"
    )
    void updateProductInfo(
            @Param("productId") Long productId,
            @Param("tag") String tag,
            @Param("title") String title,
            @Param("name") String name,
            @Param("price") Integer price,
            @Param("sido") String sido,
            @Param("sigungu") String sigungu,
            @Param("bname") String bname,
            @Param("description") String description
    );



    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.mainImage = :imageUrl WHERE p.id = :productId")
    void updateProductMainImage(@Param("imageUrl") String imageUrl, @Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Product p WHERE p.id=:productId")
    void deleteAllbyId(@Param("productId") Long productId);
}
