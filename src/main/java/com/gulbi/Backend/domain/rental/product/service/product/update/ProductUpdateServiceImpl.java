package com.gulbi.Backend.domain.rental.product.service.product.update;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryBusinessService;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
  ToDo : 업데이트 서비스는 동작은 하나, 코드의 결합도가 너무 높아. 리팩터링때 전부 분리할 예정임. 임시임
         UpdateService 숨만쉰채... 발견...
 */

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdatingService{
    private final ProductCrudService productCrudService;
    private final CategoryBusinessService categoryBusinessService;
    private final ImageService imageService;
    @Override
    public void updateProductViews(Long productId){
        resolveProduct(productId); // 유효성 검사
        productCrudService.updateProductViews(productId);
    }

    //ToDo: 람다식들 순서 배열 중요함, 추후 분리 할때 해당 메서드를 퍼사드로 하고, 나머지는 분리
    //
    @Override
    public void updateProductInfo(ProductInfoUpdateDto productInfoUpdateDto, ProductImageInfoUpdateDto productImageInfoUpdateDto){
        if(productImageInfoUpdateDto !=null){
            updateProductTextInfo(productInfoUpdateDto);
        }
        if(productImageInfoUpdateDto !=null) {
            imageService.updateProductImages(productImageInfoUpdateDto);
        }
    }


    private void updateProductTextInfo(ProductInfoUpdateDto productInfoUpdateDto){
        ProductUpdateRequestDto productUpdateRequestDto = ProductUpdateRequestDto.of();
        CategoryInProductDto categoryInProductDto = null;
        ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto = null;

        if(productInfoUpdateDto.getProductUpdateRequestDto()!=null){
            productUpdateRequestDto = productInfoUpdateDto.getProductUpdateRequestDto();
        }

        if(productInfoUpdateDto.getProductCategoryUpdateRequestDto() !=null) {
            productCategoryUpdateRequestDto = productInfoUpdateDto.getProductCategoryUpdateRequestDto();
            categoryInProductDto = categoryBusinessService.resolveCategories(productCategoryUpdateRequestDto);
        }

        productUpdateRequestDto.setCategoryInProduct(categoryInProductDto);

        Long productId = productInfoUpdateDto.getProductId();
        productUpdateRequestDto.setProductId(productId);



        productCrudService.updateProductInfo(productUpdateRequestDto);

    }


    private Product resolveProduct(Long productId){
        return productCrudService.getProductById(productId);
    }

}