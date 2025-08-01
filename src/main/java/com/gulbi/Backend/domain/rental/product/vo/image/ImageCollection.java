package com.gulbi.Backend.domain.rental.product.vo.image;

import com.gulbi.Backend.domain.rental.product.code.ImageErrorCode;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.exception.ImageVoException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

import java.util.ArrayList;
import java.util.List;

public class ImageCollection {
    private final String className = this.getClass().getName();
    private final List<Image> imageList;

    private ImageCollection(List<Image> imageList) {
        this.imageList = imageList;
    }

    public static ImageCollection of(List<Image> imageList){
        return new ImageCollection(imageList);
    }

    public List<Image> getImages(){
        if(this.imageList.isEmpty()){
            ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                    .Builder()
                    .className(className)
                    .responseApiCode(ImageErrorCode.IMAGE_COLLECTION_IS_EMPTY)
                    .build();
            throw  new ImageVoException.ImageCollectionIsEmptyException(exceptionMetaData);
        }
        return new ArrayList<>(this.imageList);
    }
}
