package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.image.ImageCrudService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
@Component
public class ImageDeleteStrategy extends AbstractImageUpdateStrategy{
	private final ImageCrudService imageCrudService;

	public ImageDeleteStrategy(ProductCrudService productCrudService, ImageCrudService imageCrudService) {
		super(productCrudService);
		this.imageCrudService = imageCrudService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeDeletedImages()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		if (!command.getToBeDeletedImages().getImagesId().isEmpty()) {
			resolveProduct(command.getProductId());
			imageCrudService.deleteImages(command.getToBeDeletedImages());
		}
			//지우려는게 메인이미지 일때 예외처리 해주긴해야함.. todo..

	}
}
