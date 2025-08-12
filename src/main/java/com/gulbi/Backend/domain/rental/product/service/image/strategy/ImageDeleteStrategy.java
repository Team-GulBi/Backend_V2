package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
@Component
public class ImageDeleteStrategy extends AbstractImageUpdateStrategy{
	private final ImageRepoService imageRepoService;

	public ImageDeleteStrategy(ProductRepoService productRepoService, ImageRepoService imageRepoService) {
		super(productRepoService);
		this.imageRepoService = imageRepoService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeDeletedImages()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		if (!command.getToBeDeletedImages().getImagesId().isEmpty()) {
			resolveProduct(command.getProductId());
			imageRepoService.deleteByIds(command.getToBeDeletedImages().getImagesId());
		}

	}
}
