package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductContentUpdateCommand;

public interface ProductUpdateStrategy {
	// 해당 요청이 내가 처리 할 전략인지 판별하는 메서드
	boolean canUpdate(ProductContentUpdateCommand command);
	// 해당 요청이 내가 처리 할 수 있는 전략이라면 처리(상품본문 수정, 카테고리 수정)
	void update(ProductContentUpdateCommand command);
}
