package com.gulbi.Backend.domain.rental.product.vo;

import com.gulbi.Backend.domain.rental.product.code.ImageErrorCode;
import com.gulbi.Backend.domain.rental.product.exception.ImageException;
import com.gulbi.Backend.domain.rental.product.exception.ImageVoException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;

import lombok.Getter;

import java.util.regex.Pattern;

public class ImageUrl {
    private final String className = this.getClass().getName();
    private static final String REGEX = "^https://.*";
    @Getter
    private final String imageUrl;

    private ImageUrl(String imageUrl) {
        ValidateImageUrl(imageUrl);
        this.imageUrl = imageUrl;
    }

    public static ImageUrl of(String imageUrl){
        return new ImageUrl(imageUrl);
    }

    private void ValidateImageUrl(String imageUrl){
        if (!Pattern.matches(REGEX, imageUrl)){
            throw new ImageException(ExceptionMetaDataFactory.of(imageUrl,className,null,ImageErrorCode.NOT_VALIDATED_IMAGE_URL));
        }
    }


}
