package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public class NewProductImageRequest {
    @Valid
    private final ProductImageFiles productImageFiles;

    @Setter
    @Getter
    @Schema(hidden = true)
    private Long productId;


    private NewProductImageRequest(List<MultipartFile> images) {
        this.productImageFiles = ProductImageFiles.of(images);
    }

    public static NewProductImageRequest of(List<MultipartFile> images){
        return new NewProductImageRequest(images);
    }

    public ProductImageFiles getProductImageFiles(){
        if(!productImageFiles.isEmpty()){
            return this.productImageFiles;
        }
        return null;
    }



}
