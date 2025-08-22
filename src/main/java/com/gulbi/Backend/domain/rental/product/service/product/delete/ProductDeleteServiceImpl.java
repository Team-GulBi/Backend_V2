package com.gulbi.Backend.domain.rental.product.service.product.delete;

import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.contract.contract.service.ContractTemplateRepoService;
import com.gulbi.Backend.domain.rental.product.entity.Product;
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
    private final ContractTemplateRepoService contractTemplateRepoService;

    @Override
    public void deleteProductInfo(Long productId) {
        // 해당 상품이 존재하는지 검증
        Product product = productRepoService.findProductById(productId);
        //soft delete
        product.markAsDeleted();

        // 상품이 존재 한다면 해당상품과 관련된 이미지 지우기(Hard Delete)
        imageRepoService.deleteAllByProduct(product);

        // 리뷰는 안자움
        // reviewRepoService.deleteAllByProduct(product);

        // 템플릿 제거
        contractTemplateRepoService.deleteByProduct(product);
    }


}
