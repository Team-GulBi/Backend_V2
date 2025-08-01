package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductImageDeleteRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageDtoCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;

public interface ImageCrudService {
    public void registerImagesWithProduct(ImageUrlCollection imageUrlCollection, Product product);
    public ProductImageDtoCollection getImageByProductId(Long productId);
    void saveMainImage(ImageUrl mainImageUrl, Product product);
    public void clearMainImageFlags(Product product);
    public void saveImages(ImageCollection imageCollection);
    public void deleteImages(ProductImageDeleteRequestDto productImageDeleteRequestDto);
    public void removeAllImagesFromProduct(Long productId);
    public ImageUrlCollection uploadImagesToS3(ProductImageCollection productImageCollection);
    public void updateMainImageFlags(ProductMainImageUpdateDto productMainImageUpdateDto);


}
