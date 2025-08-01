//package com.gulbi.Backend.domain.rental.product.controller;
//
//import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductImageDeleteRequestDto;
//import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductExistingMainImageUpdateRequestDto;
//import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
//import com.gulbi.Backend.global.response.RestApiResponse;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.Content;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@RestController("/api/v1")
//@RequiredArgsConstructor
//public class ImageController {
//    private final ImageService imageService;
//    @PatchMapping(value = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<RestApiResponse> update(@Parameter(description = "추가되는 상품 사진(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "addingImages",required = false) List<MultipartFile> toBeAddedImages, //=> ProductImageCreateRequestDto
//                                                  @Parameter(description = "교체 할 메인이미지 파일(file)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainImageFile",required = false) List<MultipartFile> toBeUpdatedMainImageFile, //=> ProductImageCreateRequestDto
//                                                  @Parameter(description = "교체 할 메인이미지 Url(string)",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "toBeUpdatedMainimageUrl",required = false) ProductExistingMainImageUpdateRequestDto toBeUpdatedMainImageWithUrl,
//                                                  @Parameter(description = "지울 상품 이미지의 아이디",content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))@RequestPart(value = "deletedImageId", required = false) ProductImageDeleteRequestDto toBeDeletedImages){
//
//        imageService.updateProductImages(toBeAddedImages, toBeUpdatedMainImageFile, toBeUpdatedMainImageWithUrl, toBeDeletedImages);
//
//    }
//}
