package com.gulbi.Backend.domain.rental.product.service.product.register;

import com.gulbi.Backend.domain.rental.product.dto.product.request.register.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.factory.ProductFactory;
import com.gulbi.Backend.domain.rental.product.service.image.ImageCrudService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRegistrationServiceImpl implements ProductRegistrationService{
    private final ImageCrudService imageCrudService;
    private final ProductFactory productFactory;
    private final ProductCrudService productCrudService;

    @Override
    public Long registerProduct(ProductRegisterRequestDto productRegisterRequestDto, NewProductImageRequest newProductImageRequest, ProductMainImageCreateRequestDto productMainImageCreateRequestDto){
        ImageUrlCollection imageUrlCollection = uploadImages(newProductImageRequest.getProductImageCollection());
        ImageUrl mainImageUrl = uploadImages(productMainImageCreateRequestDto.getProductImageCollection()).getMainImageUrl();
        productRegisterRequestDto.setMainImage(mainImageUrl);
        Product product = createWithRegisterRequestDto(productRegisterRequestDto);
        Long savedProductId = saveProduct(product);
        saveImages(imageUrlCollection,product);
        saveMainImage(mainImageUrl,product);
        return savedProductId;
    }

    private ImageUrlCollection uploadImages(ProductImageCollection productImageCollection){
        return imageCrudService.uploadImagesToS3(productImageCollection);
    }

    private Product createWithRegisterRequestDto(ProductRegisterRequestDto productRegisterRequestDto){
        return productFactory.createWithRegisterRequestDto(productRegisterRequestDto);
    }
    private Long saveProduct(Product product){
        return productCrudService.saveProduct(product);
    }
    private void saveImages(ImageUrlCollection imageUrlCollection, Product product){
        imageCrudService.registerImagesWithProduct(imageUrlCollection,product);
    }
    private void saveMainImage(ImageUrl imageUrl, Product product){
        imageCrudService.saveMainImage(imageUrl,product);
    }
}
