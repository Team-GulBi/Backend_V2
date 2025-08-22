package com.gulbi.Backend.domain.rental.product.controller;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateCreateRequest;
import com.gulbi.Backend.domain.rental.product.code.ProductSuccessCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageDeleteRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.MainImageUrlUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductTextUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.service.product.ProductService;
import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;
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
import java.util.Optional;

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
            @Parameter(description = "상품정보", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart("product") ProductRegisterRequest productInfo,
            @Parameter(description = "계약서 템플릿", required = true, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
            @RequestPart("template") TemplateCreateRequest template,
            @Parameter(description = "메인 이미지 파일",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE), required = true)
            @RequestPart(value = "mainImage", required = true) List<MultipartFile> productMainImage,
            @Parameter(description = "상품 이미지 파일", content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE), required = false)
            @RequestPart(value = "images", required = false) List<MultipartFile> productImages)

    {
            //request를 조합하여 command객체 생성
                //이미지는 필수값이 아니므로 널체크
            ProductImageFiles imageFiles = (productImages == null || productImages.isEmpty()) ? null : ProductImageFiles.of(productImages);
                //메인 이미지 및 그 외 정보는 필숫값
            ProductImageFiles mainImageFile = ProductImageFiles.of(productMainImage);
            ProductRegisterCommand command = new ProductRegisterCommand(productInfo, template,imageFiles,mainImageFile);
            //서비스 요청
            Long savedProductId= productService.registrationProduct(command);
            RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_REGISTER_SUCCESS,savedProductId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    @Operation(
            summary = "상품의 상세정보 조회",
            description = "상품의 PK값으로 상품의 상세정보 조회"
    )
    public ResponseEntity<RestApiResponse> productDetail(@PathVariable("productId") Long productId) {
        ProductDetailResponse data = productService.getProductDetail(productId);
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
        ProductSearchRequest productSearchRequest = ProductSearchRequest.of(detail, query);
        List<ProductOverViewResponse> data = productService.searchProductOverview(productSearchRequest);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_FOUND_SUCCESS,data);
        return ResponseEntity.ok(response);
    }


    @RequestBody(content = @Content(
            encoding = @Encoding(name = "request", contentType = MediaType.APPLICATION_JSON_VALUE)))
    @PatchMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "상품정보 수정",
            description = "1.상품정보중 텍스트만 수정 2.상품의 이미지 추가 3.카테고리 변경 4.이미지 삭제 위 4가지 케이스 중 적어도 1개는 들어가야함."
    )
    public ResponseEntity<RestApiResponse> updateProduct(@Parameter(description = "업데이트 할 상품의 텍스트 정보(json)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "productInfo",required = false) ProductTextUpdateRequest toBeUpdatedProductInfo,
                                                         @Parameter(description = "업데이트 할 상품의 카테고리 아이디",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "category", required = false) ProductCategoryUpdateRequest toBeUpdatedCategories,
                                                         @Parameter(description = "추가되는 상품 사진(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "addingImages",required = false) List<MultipartFile> toBeAddedImages, //=> NewProductImageRequest
                                                         @Parameter(description = "교체 할 메인이미지 파일(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainImageFile",required = false) List<MultipartFile> toBeUpdatedMainImageFile, //=> NewProductImageRequest
                                                         @Parameter(description = "교체 할 메인이미지 Url(string)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainimageUrl",required = false) MainImageUrlUpdateRequest toBeUpdatedMainImageWithUrl,
                                                         @Parameter(description = "지울 상품 이미지의 아이디",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "deletedImageId", required = false) ProductImageDeleteRequest toBeDeletedImages,
                                                         @PathVariable("productId")Long productId){

        //상품정보 업데이트 요청
        ProductTextUpdateRequest productTextUpdateRequest =
            Optional
                .ofNullable(toBeUpdatedProductInfo)
                .orElse(null);

        //상품정보(카테고리) 업데이트 요청
        ProductCategoryUpdateRequest productCategoryUpdateRequest =
			toBeUpdatedCategories;

        //새로운 상품이미지 추가 요청
        NewProductImageRequest newProductImageRequest =
            Optional
                .ofNullable(toBeAddedImages)
                .map(NewProductImageRequest::of)
                .orElse(null);

        //새로운 상품 메인이미지 추가 요청
        NewProductImageRequest newProductMainImageRequest =
            Optional
                .ofNullable(toBeUpdatedMainImageFile)
                .map(NewProductImageRequest::of)
                .orElse(null);

        //새로운 상품 메인이미지 추가 요청
        MainImageUrlUpdateRequest mainImageUrlUpdateRequest =
            Optional
                .ofNullable(toBeUpdatedMainImageWithUrl)
                .orElse(null);

        //상품 이미지 삭제 요청
        ProductImageDeleteRequest productImageDeleteRequest =
            Optional
                .ofNullable(toBeDeletedImages)
                .orElse(null);

        //이미지를 제외 한 상품 삭제 정보를 담는 DTO
        ProductContentUpdateCommand productContentUpdateCommand =
            ProductContentUpdateCommand
                .of(productTextUpdateRequest, productCategoryUpdateRequest, productId);

        //상품 이미지에 관련 된 정보를 담는 DTO
        ProductImageUpdateCommand productImageUpdateCommand =
            ProductImageUpdateCommand
                .of(newProductImageRequest, newProductMainImageRequest, mainImageUrlUpdateRequest,
                    productImageDeleteRequest, productId);


        productService.updateProduct(productContentUpdateCommand, productImageUpdateCommand);
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


