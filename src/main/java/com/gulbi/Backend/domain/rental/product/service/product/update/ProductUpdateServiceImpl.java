package com.gulbi.Backend.domain.rental.product.service.product.update;

import java.util.List;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.update.Strategy.ProductUpdateStrategy;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductUpdateServiceImpl implements ProductUpdatingService {
    //ToDo: 전략으로 빼서 없애버리기.
    private final ProductRepoService productRepoService;
    private final ImageService imageService;
    private final List<ProductUpdateStrategy> updateStrategies;

    @Override
    public void updateProductViews(Long productId) {
        resolveProduct(productId); // 유효성 검사
        productRepoService.updateProductViews(productId);
    }

    @Override
    public void updateProductInfo(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand) {
        // 전략 패턴
        if (productContentUpdateCommand != null) {
            updateStrategies.stream()
                .filter(strategy -> strategy.canUpdate(productContentUpdateCommand))
                .forEach(strategy -> strategy.update(productContentUpdateCommand));
        }

        if (productImageUpdateCommand != null) {
            imageService.updateProductImages(productImageUpdateCommand);
        }
    }

    private Product resolveProduct(Long productId) {
        return productRepoService.getProductById(productId);
    }
}
