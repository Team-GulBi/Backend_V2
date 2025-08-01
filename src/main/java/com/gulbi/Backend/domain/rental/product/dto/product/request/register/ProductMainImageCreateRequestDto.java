package com.gulbi.Backend.domain.rental.product.dto.product.request.register;

import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class ProductMainImageCreateRequestDto {

    private final ProductImageCollection mainImage;

    private ProductMainImageCreateRequestDto(List<MultipartFile> mainImage) {
        this.mainImage = ProductImageCollection.of(mainImage);
    }

    public static ProductMainImageCreateRequestDto of(List<MultipartFile> mainImage){
        return new ProductMainImageCreateRequestDto(mainImage);
    }

    public ProductImageCollection getProductImageCollection(){
        if(!mainImage.isEmpty()){
            return this.mainImage;
        }
        return null;
    }
}
