package com.gulbi.Backend.domain.rental.product.repository;

import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Long > {
    @Query("SELECT i FROM Image i WHERE i.product.id = :productId")
    List<Image> findImagesByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Transactional
    @Modifying
    @Query("UPDATE Image i SET i.main = false WHERE i.product.id = :productId")
    void updateMainFalseByProductId(@Param("productId") Long productId);

    @Transactional
    @Modifying
    @Query("UPDATE Image i SET i.main = true WHERE i.url = :imageUrl")
    void updateMainTrueByUrl(@Param("imageUrl") String imageUrl);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.product.id=:productId")
    void deleteAllImagesByProductId(@Param("productId")Long productId);

    @Transactional
    @Modifying
    void deleteAllByProduct(Product product);

}
