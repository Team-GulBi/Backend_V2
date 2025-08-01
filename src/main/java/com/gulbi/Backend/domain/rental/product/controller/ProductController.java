package com.gulbi.Backend.domain.rental.product.controller;

import com.gulbi.Backend.domain.rental.product.code.ProductSuccessCode;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductInfoUpdateDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.*;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductMainImageCreateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductExistingMainImageUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductUpdateRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.response.ProductDetailResponseDto;
import com.gulbi.Backend.domain.rental.product.service.product.ProductService;
import com.gulbi.Backend.global.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "상품등록",
            description = "상품정보, 상품이미지를 이용하여 상품을 등록 합니다."
    )
    public ResponseEntity<RestApiResponse> register(
            @Parameter(description = "상품정보", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)) @RequestPart("body") ProductRegisterRequestDto productInfo,
            @Parameter(description = "상품 이미지 파일", required = true)
            @RequestPart("images") List<MultipartFile> productImages,
            @RequestPart("mainImage") List<MultipartFile> productMainImage)
    {
            Long savedProductId=productService.registrationProduct(productInfo, ProductImageCreateRequestDto.of(productImages), ProductMainImageCreateRequestDto.of(productMainImage));
            RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_REGISTER_SUCCESS,savedProductId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "상품의 상세정보 조회",
            description = "상품의 PK값으로 상품의 상세정보 조회"
    )
    public ResponseEntity<RestApiResponse> productDetail(@PathVariable("productId") Long productId) {
        ProductDetailResponseDto data = productService.getProductDetail(productId);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_FOUND_SUCCESS,data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(
            summary = "조건에 맞는 상품 검색",
            description = "query는 검색어, detail은 필터(태그별,제목별,위치별 등등)"
    )
    public ResponseEntity<RestApiResponse> searchProduct(
            @Parameter(description = "검색어", required = true) @RequestParam("query") String query,
            @Parameter(description = "필터", required = true) @RequestParam("detail") String detail){
        ProductSearchRequestDto productSearchRequestDto = ProductSearchRequestDto.of(detail, query);
        List<ProductOverViewResponse> data = productService.searchProductOverview(productSearchRequestDto);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_FOUND_SUCCESS,data);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{productId}/views")
    @Operation(
            summary = "상품 조회수 갱신(조회수 UP)",
            description = "추후 리팩터링시 상품 상세 조회시 올라가도록 로직변경 예정이나, 언제가 될지 미지숫가루"
    )
    public ResponseEntity<RestApiResponse> updateProductViews(@Parameter(description = "상품아이디", required = true)@PathVariable("productId") Long productId){
        productService.updateProductViews(productId);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_VIEWS_UPDATED_SUCCESS);
        return ResponseEntity.ok(response);

    }
    @RequestBody(content = @Content(
            encoding = @Encoding(name = "request", contentType = MediaType.APPLICATION_JSON_VALUE)))
    @PatchMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "상품정보 수정",
            description = "1.상품정보중 텍스트만 수정 2.상품의 이미지 추가 3.카테고리 변경 4.이미지 삭제 위 4가지 케이스 중 적어도 1개는 들어가야함."
    )
    public ResponseEntity<RestApiResponse> updateProduct(@Parameter(description = "업데이트 할 상품의 텍스트 정보(json)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "productInfo",required = false) ProductUpdateRequestDto toBeUpdatedProductInfo,
                                                         @Parameter(description = "업데이트 할 상품의 카테고리 아이디",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "category", required = false) ProductCategoryUpdateRequestDto toBeUpdatedCategories,
                                                         @Parameter(description = "추가되는 상품 사진(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "addingImages",required = false) List<MultipartFile> toBeAddedImages, //=> ProductImageCreateRequestDto
                                                         @Parameter(description = "교체 할 메인이미지 파일(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainImageFile",required = false) List<MultipartFile> toBeUpdatedMainImageFile, //=> ProductImageCreateRequestDto
                                                         @Parameter(description = "교체 할 메인이미지 Url(string)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainimageUrl",required = false)ProductExistingMainImageUpdateRequestDto toBeUpdatedMainImageWithUrl,
                                                         @Parameter(description = "지울 상품 이미지의 아이디",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "deletedImageId", required = false)ProductImageDeleteRequestDto toBeDeletedImages,
                                                         @PathVariable("productId")Long productId){
        ProductInfoUpdateDto productInfoUpdateDto;
        ProductImageInfoUpdateDto productImageInfoUpdateDto;

        ProductUpdateRequestDto productUpdateRequestDto;
        ProductCategoryUpdateRequestDto productCategoryUpdateRequestDto;
        ProductImageCreateRequestDto productImageCreateRequestDto;
        ProductImageCreateRequestDto productImageCreateRequestDto1;
        ProductExistingMainImageUpdateRequestDto productExistingMainImageUpdateRequestDto;
        ProductImageDeleteRequestDto productImageDeleteRequestDto;

        if(toBeUpdatedProductInfo == null){
            productUpdateRequestDto =null;
        }else{
            productUpdateRequestDto = toBeUpdatedProductInfo;
        }

        if(toBeUpdatedCategories == null){
            productCategoryUpdateRequestDto =null;
        }else{
            productCategoryUpdateRequestDto = toBeUpdatedCategories;
        }

        if(toBeAddedImages == null){
            productImageCreateRequestDto =null;
        }else{
            productImageCreateRequestDto = ProductImageCreateRequestDto.of(toBeAddedImages);
        }

        if(toBeUpdatedMainImageFile == null){
            productImageCreateRequestDto1 =null;
        }else{
            productImageCreateRequestDto1 = ProductImageCreateRequestDto.of(toBeUpdatedMainImageFile);
        }

        if(toBeUpdatedMainImageWithUrl == null){
            productExistingMainImageUpdateRequestDto =null;
        }else{
            productExistingMainImageUpdateRequestDto = toBeUpdatedMainImageWithUrl;
        }

        if(toBeDeletedImages ==null){
            productImageDeleteRequestDto =null;
        }else{
            productImageDeleteRequestDto = toBeDeletedImages;
        }

        productInfoUpdateDto = ProductInfoUpdateDto.of(productUpdateRequestDto, productCategoryUpdateRequestDto, productId);
        productImageInfoUpdateDto = ProductImageInfoUpdateDto.of(productImageCreateRequestDto, productImageCreateRequestDto1, productExistingMainImageUpdateRequestDto, productImageDeleteRequestDto,productId);
        productService.updateProduct(productInfoUpdateDto, productImageInfoUpdateDto);

        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_INFO_UPDATED_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<RestApiResponse> deleteProduct(@PathVariable("productId") Long productId){
        productService.deleteProduct(productId);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_INFO_UPDATED_SUCCESS);
        return ResponseEntity.ok(response);
    }


}


