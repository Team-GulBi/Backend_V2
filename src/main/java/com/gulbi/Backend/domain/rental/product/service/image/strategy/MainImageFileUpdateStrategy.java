package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.request.register.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
@Component
public class MainImageFileUpdateStrategy extends AbstractImageUpdateStrategy{
	private final ImageRepoService imageRepoService;
	public MainImageFileUpdateStrategy(ProductRepoService productRepoService, ImageRepoService imageRepoService) {
		super(productRepoService);
		this.imageRepoService = imageRepoService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeUpdatedMainImageFile()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		Long productId = command.getProductId();
		Product product = resolveProduct(productId);
		imageRepoService.clearMainImageFlags(product);
		handleUpdatedMainImageFile(command, product);
	}
	private void handleUpdatedMainImageFile(ProductImageUpdateCommand command, Product product) {

		ImageUrlCollection imageUrlCollection = uploadImagesToS3(command.getToBeUpdatedMainImageFile());
		imageRepoService.saveMainImage(imageUrlCollection.getMainImageUrl(), product);

		ProductMainImageUpdateDto updateDto = ProductMainImageUpdateDto.of(command.getProductId(), imageUrlCollection.getMainImageUrl());
		productRepoService.updateProductMainImage(updateDto);

	}

	private ImageUrlCollection uploadImagesToS3(NewProductImageRequest request){
		return imageRepoService.uploadImagesToS3(request.getProductImageCollection());
	}



}
