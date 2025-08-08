package com.gulbi.Backend.domain.rental.product.service.product.delete;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.review.service.ReviewRepoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class ProductDeleteServiceImpl implements ProductDeleteService{
    private final ProductRepoService productRepoService;
    private final ImageRepoService imageRepoService;
    private final ReviewRepoService reviewRepoService;
    // 이미지 서비스
    // 리뷰 서비스
    @Override
    public void deleteProductInfo(Long productId) {
        //상품이 존재하는지 먼저 조회, 왜냐면 자식 엔티티를 다 지웠는데 상품에서 예외가 발생한다면 트렌젝션 시 불필요한 소요가 될 수 있으므로,
        //초기 조회 비용이 조금 증가하더라도, 검증을 하는게 좋다 판단, 상품 삭제가 서비스에서 상대적으로 많이 이루어질거 같다고 판단하지 않음.
        Optional.ofNullable(isExistProduct(productId)).ifPresent(productExist ->{
                    imageRepoService.removeAllImagesFromProduct(productId);
                    reviewRepoService.removeAllReviewsFromProductId(productId);
                    productRepoService.deleteProduct(productId);
                }
        );
    }

    private Product isExistProduct(Long productId){
        return productRepoService.getProductById(productId);
    }

}
