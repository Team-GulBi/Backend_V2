package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class ProductMainImageCreateRequestDto {

    private final ProductImageFiles mainImage;

    private ProductMainImageCreateRequestDto(List<MultipartFile> mainImage) {
        this.mainImage = ProductImageFiles.of(mainImage);
    }

    public static ProductMainImageCreateRequestDto of(List<MultipartFile> mainImage){
        return new ProductMainImageCreateRequestDto(mainImage);
    }

    public ProductImageFiles getProductImageCollection(){
        if(!mainImage.isEmpty()){
            return this.mainImage;
        }
        return null;
    }
}
