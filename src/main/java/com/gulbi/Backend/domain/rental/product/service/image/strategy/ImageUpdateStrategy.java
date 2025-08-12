package com.gulbi.Backend.domain.rental.product.service.image.strategy;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductImageUpdateCommand;

public interface ImageUpdateStrategy {
	// 해당 요청이 내가 처리 할 전략인지 판별하는 메서드
	boolean canUpdate(ProductImageUpdateCommand command);
	// 해당 요청이 내가 처리 할 수 있는 전략이라면 처리(이미지 추가, 메인이미지 변경(파일), 메인이미지 변경(url), 이미지삭제)
	void update(ProductImageUpdateCommand command);

}
