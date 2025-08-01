package com.gulbi.Backend.domain.rental.product.vo.image;

import com.gulbi.Backend.domain.rental.product.validator.ValidProductImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
@ValidProductImages
public class ProductImageCollection {
    private final List<MultipartFile> productImages;

    private ProductImageCollection(List<MultipartFile> productImages) {
        this.productImages = productImages;
    }

    public static ProductImageCollection of(List<MultipartFile> productImages){
        return new ProductImageCollection(productImages);
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
