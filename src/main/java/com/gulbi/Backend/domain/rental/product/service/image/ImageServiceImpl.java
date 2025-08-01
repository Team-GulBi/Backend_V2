package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductExistingMainImageUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//fix Todo: 상위 클레스를 호출해서 로직을 처리하니까 ProductResolver는 최상단에만 두고 추후에는 ProductId만 넘겨서 중복되지 않도록 수정.
//todo: resolveProduct가 여기서도 쓰이는데. ProductId를 crud에 넘기도록해서 그쪽에서 조회하도록 추후 변경 할 것. deleteimage 유효성검사 추가 할 것.
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageCrudService imageCrudService;
    private final ProductCrudService productCrudService;

    @Override
    public void updateProductImages(ProductImageInfoUpdateDto productImageInfoUpdateDto) {

        Long productId = productImageInfoUpdateDto.getProductId();
        Product product = resolveProduct(productId);

        if(productImageInfoUpdateDto.getToBeAddedImages() !=null) {
            handleAddedImages(productImageInfoUpdateDto, product);
        }
        if(productImageInfoUpdateDto.getToBeUpdatedMainImageFile() !=null) {
            imageCrudService.clearMainImageFlags(product);
            handleUpdatedMainImageFile(productImageInfoUpdateDto, product);
        }
        if(productImageInfoUpdateDto.getToBeUpdatedMainImageWithUrl() !=null) {
            imageCrudService.clearMainImageFlags(product);
            ProductMainImageUpdateDto productMainImageUpdateDto = ProductMainImageUpdateDto.of(productId, productImageInfoUpdateDto.getToBeUpdatedMainImageWithUrl().getMainImageUrl());
            handleUpdatedMainImageWithUrl(productMainImageUpdateDto);
        }
        if(productImageInfoUpdateDto.getToBeDeletedImages() !=null){
            handleDeletedImages(productImageInfoUpdateDto); //지우려는게 메인이미지 일때 예외처리 해주긴해야함.. todo..
        }
    }

    private void handleAddedImages(ProductImageInfoUpdateDto dto, Product product) {
            ImageUrlCollection imageUrlCollection = uploadImagesToS3(dto.getToBeAddedImages().getProductImageCollection());
            updateNewImage(imageUrlCollection, product);
    }

    private void handleUpdatedMainImageFile(ProductImageInfoUpdateDto dto, Product product) {

            ImageUrlCollection imageUrlCollection = uploadImagesToS3(dto.getToBeUpdatedMainImageFile().getProductImageCollection());
            imageCrudService.saveMainImage(imageUrlCollection.getMainImageUrl(), product);

            ProductMainImageUpdateDto updateDto = ProductMainImageUpdateDto.of(dto.getProductId(), imageUrlCollection.getMainImageUrl());
            productCrudService.updateProductMainImage(updateDto);

    }

    private void handleUpdatedMainImageWithUrl(ProductMainImageUpdateDto dto) {
        imageCrudService.updateMainImageFlags(dto);
        productCrudService.updateProductMainImage(dto);
    }

    private void handleDeletedImages(ProductImageInfoUpdateDto dto) {
        if (!dto.getToBeDeletedImages().getImagesId().isEmpty()) {
            imageCrudService.deleteImages(dto.getToBeDeletedImages());
        }
    }

    private ImageUrlCollection uploadImagesToS3(ProductImageCollection productImageCollection) {
        return imageCrudService.uploadImagesToS3(productImageCollection);
    }

    private Product resolveProduct(Long productId) {
        return productCrudService.getProductById(productId);
    }

    private void updateNewImage(ImageUrlCollection imageUrlCollection, Product product) {
        imageCrudService.registerImagesWithProduct(imageUrlCollection, product);
    }

}
