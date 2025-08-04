package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageCrudService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
@Component
public class MainImageUrlUpdateStrategy extends AbstractImageUpdateStrategy{
	private final ImageCrudService imageCrudService;

	public MainImageUrlUpdateStrategy(ImageCrudService imageCrudService, ProductCrudService productCrudService) {
		super(productCrudService);
		this.imageCrudService = imageCrudService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeUpdatedMainImageWithUrl()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		Long productId = command.getProductId();
		Product product = resolveProduct(productId);
		imageCrudService.clearMainImageFlags(product);
		ProductMainImageUpdateDto productMainImageUpdateDto = ProductMainImageUpdateDto.of(productId, command.getToBeUpdatedMainImageWithUrl().getMainImageUrl());
		handleUpdatedMainImageWithUrl(productMainImageUpdateDto);
	}
	private void handleUpdatedMainImageWithUrl(ProductMainImageUpdateDto dto) {
		imageCrudService.updateMainImageFlags(dto);
		productCrudService.updateProductMainImage(dto);
	}
}
