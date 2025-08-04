package com.gulbi.Backend.domain.rental.product.repository;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductImageDeleteRequest;
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
    @Query("SELECT new com.gulbi.Backend.domain.rental.product.dto.ProductImageDto(i.id, i.product.id, i.url,i.main) " +
            "FROM Image i WHERE i.product.id = :productId")
    public List<ProductImageDto> findByImageWithProduct(@Param("productId") Long productId);
    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.id IN :#{#dto.imagesId}")
    void deleteImages(@Param("dto") ProductImageDeleteRequest dto);

    @Transactional
    @Modifying
    @Query("UPDATE Image i SET i.main = false WHERE i.product = :product")
    void resetMainImagesByProduct(@Param("product") Product product);

    @Transactional
    @Modifying
    @Query("UPDATE Image i SET i.main = true WHERE i.url = :imageUrl")
    void updateImagesFlagsToTrue(@Param("imageUrl") String imageUrl);

    @Transactional
    @Modifying
    @Query("DELETE FROM Image i WHERE i.product=:product")
    void deleteAllImagesByProductId(@Param("product")Product product);


}
