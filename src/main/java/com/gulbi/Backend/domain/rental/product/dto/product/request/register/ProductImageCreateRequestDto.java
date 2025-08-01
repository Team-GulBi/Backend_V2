package com.gulbi.Backend.domain.rental.product.dto.product.request.register;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class ProductImageCreateRequestDto {
    @Valid
    private final ProductImageCollection productImageCollection;

    @Setter
    @Getter
    @Schema(hidden = true)
    private Long productId;

    @Setter
    @Getter
    @Schema(hidden = true)
    private Product product;

    private ProductImageCreateRequestDto(List<MultipartFile> images) {
        this.productImageCollection = ProductImageCollection.of(images);
    }

    public static ProductImageCreateRequestDto of(List<MultipartFile> images){
        return new ProductImageCreateRequestDto(images);
    }

    public ProductImageCollection getProductImageCollection(){
        if(!productImageCollection.isEmpty()){
            return this.productImageCollection;
        }
        return null;
    }



}
