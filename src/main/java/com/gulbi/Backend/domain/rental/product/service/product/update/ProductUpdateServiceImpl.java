package com.gulbi.Backend.domain.rental.product.service.product.update;

import java.util.List;

import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageUpdateService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.update.Strategy.ProductUpdateStrategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdatingService {
    //ToDo: 전략으로 빼서 없애버리기.
    private final ProductRepoService productRepoService;
    private final ImageUpdateService imageUpdateService;
    private final List<ProductUpdateStrategy> updateStrategies;

    @Override
    public void updateProductViews(Long productId) {
        Product product = productRepoService.findProductById(productId);
        product.updateView();
    }

    @Override
    public void updateProductInfo(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand) {
        // 전략 패턴
        // 상품 Text 업데이트, 전략 순회
        if (productContentUpdateCommand != null) {
            updateStrategies.stream()
                .filter(strategy -> strategy.canUpdate(productContentUpdateCommand))
                .forEach(strategy -> strategy.update(productContentUpdateCommand));
        }
        // 상품 이미지 업데이트, 전략 순회
        if (productImageUpdateCommand != null) {
            imageUpdateService.updateProductImages(productImageUpdateCommand);
        }
    }

}
