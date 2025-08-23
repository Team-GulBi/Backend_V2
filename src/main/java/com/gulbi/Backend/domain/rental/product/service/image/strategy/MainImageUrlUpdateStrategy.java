package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductMainImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;

@Component
public class MainImageUrlUpdateStrategy extends AbstractImageUpdateStrategy{
	private final ImageService imageService;

	public MainImageUrlUpdateStrategy(ProductRepoService productRepoService,
		ImageService imageService) {
		super(productRepoService);
		this.imageService = imageService;
	}

	@Override
	public boolean canUpdate(ProductImageUpdateCommand command) {
		return Optional.ofNullable(command.getToBeUpdatedMainImageWithUrl()).isPresent();
	}

	@Override
	public void update(ProductImageUpdateCommand command) {
		//메인 이미지, 바꿀 상품 아이디 추출
		ImageUrl mainImageUrl = command.getToBeUpdatedMainImageWithUrl().getMainImageUrl();
		Long productId = command.getProductId();
		//업데이트를 위한 상품 조회
		Product product = productRepoService.findById(productId);
		//업데이트
		product.updateMainImage(mainImageUrl);
		productRepoService.save(product);
		//이미지 업데이트
		ProductMainImageUpdateCommand requestCommand = ProductMainImageUpdateCommand.of(productId,mainImageUrl);
		imageService.changeMainImage(requestCommand);
	}
}
