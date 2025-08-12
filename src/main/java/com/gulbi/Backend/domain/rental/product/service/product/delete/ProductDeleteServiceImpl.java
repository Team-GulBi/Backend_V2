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
        productRepoService.findProductById(productId);
        imageRepoService.removeAllImagesByProductId(productId);
        reviewRepoService.deleteAllByProductId(productId);
        productRepoService.delete(productId);
    }


}
