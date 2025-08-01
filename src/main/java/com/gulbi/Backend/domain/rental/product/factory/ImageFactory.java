package com.gulbi.Backend.domain.rental.product.factory;

import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageCollection;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImageFactory {
    private static final boolean FALSE_MAIN_IMG = Boolean.FALSE;
    private static final boolean TRUE_MAIN_IMG = Boolean.TRUE;

    public static Image createImageToMain(ImageUrl url, Product product){
        return Image.builder().url(url.getImageUrl()).product(product).main(TRUE_MAIN_IMG).build();
    }
    public static Image createImage(ImageUrl url, Product product){
        return Image.builder().url(url.getImageUrl()).product(product).main(FALSE_MAIN_IMG).build();
    }
    public static ImageCollection createImages(ImageUrlCollection imageUrlCollection, Product product) {
        List<ImageUrl> urls = imageUrlCollection != null ? imageUrlCollection.getImageUrls() : Collections.emptyList();
        return ImageCollection.of(urls.stream()
                .map(url -> createImage(url, product))
                .collect(Collectors.toList()));
    }

}
