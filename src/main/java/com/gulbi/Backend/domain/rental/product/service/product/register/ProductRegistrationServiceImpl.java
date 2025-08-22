package com.gulbi.Backend.domain.rental.product.service.product.register;

import java.util.logging.Logger;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateCreateRequest;
import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplateFactory;
import com.gulbi.Backend.domain.contract.contract.service.ContractTemplateRepoService;
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
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRegistrationServiceImpl implements ProductRegistrationService{
    private final ImageService imageService;
    private final ImageRepoService imageRepoService;
    private final ProductRepoService productRepoService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ContractTemplateRepoService contractTemplateRepoService;

    @Override
    public Long registerProduct(ProductRegisterCommand command) {
        ProductRegisterRequest request = command.getProductRegisterRequest();
        ProductImageFiles imageFiles = command.getImageFiles();
        ProductImageFiles mainImageFile = command.getMainImageFiles();
        log.info("메인 이미지 {}" , 3);
        log.info("메인 이미지 {}" , mainImageFile);
        log.info("이미지 {}" , imageFiles);

        ImageUrls imageUrls = null;
        ImageUrl mainImageUrl = null;

        // 현재 상품 등록 요청 유저 추출
        User user = userService.getAuthenticatedUser();

        // 카테고리 추출 및 유효성 검사
        CategoryBundle categories = categoryService.resolveCategories(
            request.getBcategoryId(), request.getMcategoryId(), request.getScategoryId()
        );

        // 상품 생성
        Product product = ProductFactory.createWithRegisterRequestDto(user, categories, request);


        // 상품 이미지 S3 업로드 (옵션)
        if (imageFiles != null && !imageFiles.isEmpty()) {
            imageUrls = uploadImages(imageFiles);
        }

        // 메인 이미지 S3 업로드 (옵션)
        if (mainImageFile != null && !mainImageFile.isEmpty()) {
            mainImageUrl = uploadImages(mainImageFile).getMainImageUrl();
        }

        if (mainImageUrl != null) {
            product.updateMainImage(mainImageUrl);
        }

        productRepoService.save(product);

        // 상품 이미지 저장
        if (imageUrls != null) {
            Images productImages = imageService.createImages(imageUrls, product);
            imageRepoService.saveAll(productImages.getImages());
        }


        // 메인 이미지 저장
        if (mainImageUrl != null) {
            Image productMainImage = imageService.createMainImage(mainImageUrl, product);
            imageRepoService.save(productMainImage);
        }

        // 계약서 템플릿 생성 및 저장
        TemplateCreateRequest templateCreateRequest = command.getTemplateCreateRequest();
        ContractTemplate template = ContractTemplateFactory.createTemplate(templateCreateRequest, product);
        contractTemplateRepoService.save(template);

        return product.getId();
    }

    private ImageUrls uploadImages(ProductImageFiles productImageFiles){
        return imageService.uploadProductImagesToS3(productImageFiles);
    }

}
