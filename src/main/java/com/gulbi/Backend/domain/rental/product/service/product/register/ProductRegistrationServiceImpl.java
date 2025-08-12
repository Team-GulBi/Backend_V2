package com.gulbi.Backend.domain.rental.product.service.product.register;

import com.gulbi.Backend.domain.rental.product.dto.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterRequest;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.factory.ProductFactory;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryService;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.Images;
import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductRegistrationServiceImpl implements ProductRegistrationService{
    private final ImageService imageService;
    private final ImageRepoService imageRepoService;
    private final ProductRepoService productRepoService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Override
    public Long registerProduct(ProductRegisterCommand command){
        ProductRegisterRequest request = command.getProductRegisterRequest();
        ProductImageFiles imageFiles = command.getImageFiles();
        ProductImageFiles mainImageFile = command.getMainImageFiles();
        //상품 이미지 S3 업로드
        ImageUrls imageUrls = uploadImages(mainImageFile);
        //상품 메인 이미지 S3 업로드
        ImageUrl mainImageUrl = uploadImages(imageFiles).getMainImageUrl();

        //현제 상품 등록 요청 유저의 ID추출
        User user = userService.getAuthenticatedUser();

        //카테고리 추출 및 유효성 검사.
        CategoryBundle categories = categoryService.resolveCategories(request.getBcategoryId(), request.getMcategoryId(), request.getScategoryId());

        //상품 생성, 영속성 컨텍스트를 위해 미리 저장
        Product product = ProductFactory.createWithRegisterRequestDto(user, categories, request);
        product.updateMainImage(mainImageUrl);
        productRepoService.save(product);
        //상품 이미지들 생성
        Images productImages = imageService.createImages(imageUrls, product);
        //메인 이미지 생성
        Image productMainImage = imageService.createMainImage(mainImageUrl, product);
        //이미지 저장 후 메인이미지 저장
        imageRepoService.saveAll(productImages.getImages());
        imageRepoService.save(productMainImage);
        return product.getId();
    }

    private ImageUrls uploadImages(ProductImageFiles productImageFiles){
        return imageService.uploadProductImagesToS3(productImageFiles);
    }

}
