package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;

public abstract class AbstractImageUpdateStrategy implements ImageUpdateStrategy {
	protected final ProductRepoService productRepoService;

	protected AbstractImageUpdateStrategy(ProductRepoService productRepoService) {
		this.productRepoService = productRepoService;
	}
	protected Product resolveProduct(Long productId) {
		return productRepoService.getProductById(productId);
	}

	@Override
	public abstract boolean canUpdate(ProductImageUpdateCommand command);

	@Override
	public abstract void update(ProductImageUpdateCommand command);
}
