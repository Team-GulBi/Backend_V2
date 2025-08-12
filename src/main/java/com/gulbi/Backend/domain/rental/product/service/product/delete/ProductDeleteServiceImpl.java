package com.gulbi.Backend.domain.rental.product.service.product.delete;

import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.service.ReviewRepoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductDeleteServiceImpl implements ProductDeleteService{
    private final ProductRepoService productRepoService;
    private final ImageRepoService imageRepoService;
    private final ReviewRepoService reviewRepoService;
    @Override
    public void deleteProductInfo(Long productId) {
        // 해당 상품이 존재하는지 검증
        productRepoService.findProductById(productId);
        // 상품이 존재 한다면 해당상품과 관련된 이미지 지우기
        imageRepoService.removeAllImagesByProductId(productId);
        // 리뷰도 지우기
        reviewRepoService.deleteAllByProductId(productId);
        // 마지막으로 상품을 지우기
        productRepoService.delete(productId);
    }


}
