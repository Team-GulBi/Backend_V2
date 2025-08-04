package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.product.request.update.productTextUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
@Component
public class ProductTextInfoUpdateStrategy implements ProductUpdateStrategy{
	private final ProductCrudService productCrudService;

	public ProductTextInfoUpdateStrategy(ProductCrudService productCrudService) {
		this.productCrudService = productCrudService;
	}

	@Override
	public boolean canUpdate(ProductContentUpdateCommand command) {
		return Optional.ofNullable(command.getProductTextUpdateRequest()).isPresent();
	}

	@Override
	public void update(ProductContentUpdateCommand command) {
		//어차피 여기에 아이디도 같이 잇음
		productCrudService.updateProductTextOnly(command); // 카테고리 제외한 텍스트만 업데이트

	}
}
