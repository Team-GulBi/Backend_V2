package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;

public abstract class AbstractImageUpdateStrategy implements ImageUpdateStrategy {
	protected final ProductRepoService productRepoService;

	protected AbstractImageUpdateStrategy(ProductRepoService productRepoService) {
		this.productRepoService = productRepoService;
	}
	protected void resolveProduct(Long productId) {
		productRepoService.findById(productId);
	}

	@Override
	public abstract boolean canUpdate(ProductImageUpdateCommand command);

	@Override
	public abstract void update(ProductImageUpdateCommand command);
}
