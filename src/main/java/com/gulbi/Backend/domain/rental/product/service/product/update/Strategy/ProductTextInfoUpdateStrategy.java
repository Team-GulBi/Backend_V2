package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
@Component
public class ProductTextInfoUpdateStrategy implements ProductUpdateStrategy{
	private final ProductRepoService productRepoService;

	public ProductTextInfoUpdateStrategy(ProductRepoService productRepoService) {
		this.productRepoService = productRepoService;
	}

	@Override
	public boolean canUpdate(ProductContentUpdateCommand command) {
		return Optional.ofNullable(command.getProductTextUpdateRequest()).isPresent();
	}

	@Override
	public void update(ProductContentUpdateCommand command) {
		//어차피 여기에 아이디도 같이 잇음
		productRepoService.updateProductTextOnly(command); // 카테고리 제외한 텍스트만 업데이트

	}
}
