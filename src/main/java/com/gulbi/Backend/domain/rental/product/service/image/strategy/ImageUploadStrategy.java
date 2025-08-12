package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoJpaService;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.image.Images;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageFiles;

@Component
public class ImageUploadStrategy extends AbstractImageUpdateStrategy{
	private final ImageService imageService;
	private final ImageRepoJpaService imageRepoJpaService;

	public ImageUploadStrategy(ProductRepoService productRepoService, ImageService imageService,
		ImageRepoJpaService imageRepoJpaService) {
		super(productRepoService);
		this.imageService = imageService;
		this.imageRepoJpaService = imageRepoJpaService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeAddedImages()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		NewProductImageRequest request = command.getToBeAddedImages();
		ProductImageFiles newFiles = request.getProductImageFiles();
		//파일 => url =
		Long productId = command.getProductId();
		ImageUrls imageUrls = imageService.uploadProductImagesToS3(newFiles);//파일 집어넣고
		Product product = productRepoService.findProductById(productId);
		Images images = imageService.createImages(imageUrls, product);
		imageRepoJpaService.saveAll(images.getImages());

	}


}
