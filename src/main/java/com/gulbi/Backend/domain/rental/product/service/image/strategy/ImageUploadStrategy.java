package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.request.register.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
@Component
public class ImageUploadStrategy extends AbstractImageUpdateStrategy{
	private final ImageRepoService imageRepoService;
	public ImageUploadStrategy(ProductRepoService productRepoService, ImageRepoService imageRepoService) {
		super(productRepoService);
		this.imageRepoService = imageRepoService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeAddedImages()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		Long productId = command.getProductId();
		Product product = resolveProduct(productId);
		ImageUrlCollection imageUrlCollection = uploadImagesToS3(command.getToBeAddedImages());
		updateNewImage(imageUrlCollection, product);
	}

	private ImageUrlCollection uploadImagesToS3(NewProductImageRequest request){
		return imageRepoService.uploadImagesToS3(request.getProductImageCollection());
	}
	private void updateNewImage(ImageUrlCollection uploadedImages, Product product){
		imageRepoService.registerImagesWithProduct(uploadedImages, product);
	}
}
