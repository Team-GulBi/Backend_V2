package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.dto.ProductMainImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.Images;
import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;

public interface ImageService {
    ImageUrls uploadProductImagesToS3(ProductImageFiles productImageFiles);
    Images createImages(ImageUrls imageUrls, Product product);
    Image createMainImage(ImageUrl imageUrl, Product product);
    void changeMainImage(ProductMainImageUpdateCommand productMainImageUpdateCommand);


}
