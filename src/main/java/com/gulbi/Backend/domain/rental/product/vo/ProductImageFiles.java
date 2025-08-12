package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.validator.ValidProductImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@ValidProductImages
public class ProductImageFiles {
    private final List<MultipartFile> productImages;

    private ProductImageFiles(List<MultipartFile> productImages) {
        this.productImages = productImages;
    }

    public static ProductImageFiles of(List<MultipartFile> productImages){
        return new ProductImageFiles(productImages);
    }

    public List<MultipartFile> getProductImages(){
        return new ArrayList<>(productImages);
    }

    public boolean isEmpty(){
        if(this.productImages.isEmpty() || this.productImages == null){
            return true;
        }
        return false;

    }

}
