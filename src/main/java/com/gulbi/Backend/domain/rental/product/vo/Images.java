package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.code.ImageErrorCode;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.exception.ImageException;
import com.gulbi.Backend.domain.rental.product.exception.ImageVoException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.GlobalErrorCode;

import java.util.ArrayList;
import java.util.List;

public class Images {
    private final String className = this.getClass().getName();
    private final List<Image> imageList;

    private Images(List<Image> imageList) {
        this.imageList = imageList;
    }

    public static Images of(List<Image> imageList){
        return new Images(imageList);
    }

    public List<Image> getImages(){
        if(this.imageList.isEmpty()){
            throw new ImageException(ExceptionMetaDataFactory.of(null, className,null, GlobalErrorCode.LIST_IS_EMPTY));
        }
        return new ArrayList<>(this.imageList);
    }
}
