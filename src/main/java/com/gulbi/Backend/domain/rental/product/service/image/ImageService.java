package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductMainImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.image.Images;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageFiles;

public interface ImageService {
    ImageUrls uploadProductImagesToS3(ProductImageFiles productImageFiles);
    Images createImages(ImageUrls imageUrls, Product product);
    Image createMainImage(ImageUrl imageUrl, Product product);
    void changeMainImage(ProductMainImageUpdateCommand productMainImageUpdateCommand);


}
