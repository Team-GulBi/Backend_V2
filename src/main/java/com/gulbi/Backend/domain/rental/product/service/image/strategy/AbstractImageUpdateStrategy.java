package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;

public abstract class AbstractImageUpdateStrategy implements ImageUpdateStrategy {
	protected final ProductCrudService productCrudService;

	protected AbstractImageUpdateStrategy(ProductCrudService productCrudService) {
		this.productCrudService = productCrudService;
	}
	protected Product resolveProduct(Long productId) {
		return productCrudService.getProductById(productId);
	}

	@Override
	public abstract boolean canUpdate(ProductImageUpdateCommand command);

	@Override
	public abstract void update(ProductImageUpdateCommand command);
}
