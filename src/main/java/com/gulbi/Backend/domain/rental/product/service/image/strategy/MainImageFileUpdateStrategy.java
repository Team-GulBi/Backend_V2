package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.NewProductImageRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductMainImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.factory.ImageFactory;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;

@Component
public class MainImageFileUpdateStrategy extends AbstractImageUpdateStrategy{
	private final ImageRepoService imageRepoService;
	private final ImageService imageService;
	public MainImageFileUpdateStrategy(ProductRepoService productRepoService, ImageRepoService imageRepoService,
		ImageService imageService) {
		super(productRepoService);
		this.imageRepoService = imageRepoService;
		this.imageService = imageService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeUpdatedMainImageFile()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		// 컨트롤러에서 받은 request 추출
		NewProductImageRequest request = command.getToBeUpdatedMainImageFile();
		// Request에서 받은 상품 이미지, 상품 아이디 추출
		ProductImageFiles productImageFiles = request.getProductImageFiles();
		Long productId = command.getProductId();
		//업데이트를 위한 상품 조회
		Product product = productRepoService.findById(productId);
		//S3버킷 업로드
		ImageUrls imageUrls = imageService.uploadProductImagesToS3(productImageFiles);
		//추출된 url중 메인 이미지 추출
		ImageUrl mainImageUrl = imageUrls.getMainImageUrl();
		// 상품의 메인이미지 필드 업데이트
		product.updateMainImage(mainImageUrl);
		productRepoService.save(product);
		// 이미지 업데이트를 위한 command 생성 및 요청
		Image mainImage = ImageFactory.createMainImage(mainImageUrl, product);
		imageRepoService.save(mainImage);

		ProductMainImageUpdateCommand requestCommand = ProductMainImageUpdateCommand.of(productId,mainImageUrl);
		imageService.changeMainImage(requestCommand);
	}



}
