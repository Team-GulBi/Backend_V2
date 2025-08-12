package com.gulbi.Backend.domain.rental.product.factory;

import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.image.Images;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ImageFactory {
    private static final boolean FALSE_MAIN_IMG = Boolean.FALSE;
    private static final boolean TRUE_MAIN_IMG = Boolean.TRUE;

    public static Image createMainImage(ImageUrl url, Product product){
        return Image.builder().url(url.getImageUrl()).product(product).main(TRUE_MAIN_IMG).build();
    }
    public static Image createImage(ImageUrl url, Product product){
        return Image.builder().url(url.getImageUrl()).product(product).main(FALSE_MAIN_IMG).build();
    }
    public static Images createImages(ImageUrls imageUrls, Product product) {
        List<ImageUrl> urls = imageUrls != null ? imageUrls.getImageUrls() : Collections.emptyList();
        return Images.of(urls.stream()
                .map(url -> createImage(url, product))
                .collect(Collectors.toList()));
    }

}
