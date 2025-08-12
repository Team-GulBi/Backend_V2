package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.exception.ImageException;
import com.gulbi.Backend.domain.rental.product.exception.ImageVoException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.GlobalErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageUrls {
    private final List<ImageUrl> imageUrlList;
    private final String CLASS_NAME = this.getClass().getName();
    private ImageUrls(List<ImageUrl> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
    public static ImageUrls of(List<ImageUrl> imageUrlList){
        if (imageUrlList.isEmpty()){
            throw new ImageException(ExceptionMetaDataFactory.of(null,null,null, GlobalErrorCode.LIST_IS_EMPTY));
        }
        return new ImageUrls(imageUrlList);
    }
    public List<ImageUrl> getImageUrls(){
        if(imageUrlList.isEmpty()){
            throw new ImageException(ExceptionMetaDataFactory.of(null,CLASS_NAME,null, GlobalErrorCode.LIST_IS_EMPTY));
        }
        return new ArrayList<>(imageUrlList);
    }
    public ImageUrl getMainImageUrl() {
        return getImageUrls().isEmpty() ? null : getImageUrls().get(0);
    }

    public ImageUrls append(ImageUrl imageUrl) {
        if(imageUrlList.isEmpty()){
            throw new ImageException(ExceptionMetaDataFactory.of(null,CLASS_NAME,null, GlobalErrorCode.LIST_IS_EMPTY));
        }
        List<ImageUrl> newImageUrls = new ArrayList<>(this.imageUrlList);
        newImageUrls.add(imageUrl);
        return new ImageUrls(newImageUrls);
    }



}
