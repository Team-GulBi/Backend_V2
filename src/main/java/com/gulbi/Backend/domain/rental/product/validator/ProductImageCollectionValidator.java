package com.gulbi.Backend.domain.rental.product.validator;

import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageFiles;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.io.IOException;
import java.util.List;

public class ProductImageCollectionValidator implements ConstraintValidator<ValidProductImages, ProductImageFiles> {

    @Override
    public void initialize(ValidProductImages constraintAnnotation) {
        // 초기화할 내용이 없다면 비워둡니다.
    }

    public boolean isValid(ProductImageFiles productImageFiles, ConstraintValidatorContext context) {
        if (productImageFiles == null || productImageFiles.isEmpty()) {
            return false;
        }

        List<MultipartFile> images = productImageFiles.getProductImages();
        Tika tika = new Tika(); // Tika 객체 생성

        for (MultipartFile image : images) {
            String filename = image.getOriginalFilename();
            if (filename == null || (!filename.endsWith(".png") && !filename.endsWith(".jpg") && !filename.endsWith(".jpeg"))) {
                return false;
            }

            // 파일 MIME 타입을 Tika를 사용하여 확인
            try {
                String contentType = tika.detect(image.getInputStream()); // 실제 MIME 타입을 추출
                if (!contentType.equals("image/png") && !contentType.equals("image/jpeg") && !contentType.equals("image/jpg")) {
                    return false;
                }
            } catch (IOException e) {
                // 예외 처리
                return false;
            }
        }

        return true;
    }
}
