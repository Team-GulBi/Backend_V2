package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;

@Component
public class MainImageUrlUpdateStrategy extends AbstractImageUpdateStrategy{
	private final ImageRepoService imageRepoService;

	public MainImageUrlUpdateStrategy(ImageRepoService imageRepoService, ProductRepoService productRepoService) {
		super(productRepoService);
		this.imageRepoService = imageRepoService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeUpdatedMainImageWithUrl()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		Long productId = command.getProductId();
		Product product = resolveProduct(productId);
		imageRepoService.clearMainImageFlags(product);
		ProductMainImageUpdateDto productMainImageUpdateDto = ProductMainImageUpdateDto.of(productId, command.getToBeUpdatedMainImageWithUrl().getMainImageUrl());
		handleUpdatedMainImageWithUrl(productMainImageUpdateDto);
	}
	private void handleUpdatedMainImageWithUrl(ProductMainImageUpdateDto dto) {
		imageRepoService.updateMainImageFlags(dto);
		productRepoService.updateProductMainImage(dto);
	}
}
