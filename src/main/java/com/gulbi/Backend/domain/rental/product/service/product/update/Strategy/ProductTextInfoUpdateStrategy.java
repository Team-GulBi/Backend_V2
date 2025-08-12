package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductTextUpdateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Product;
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
		// 상품 아이디 조회
		Long productId = command.getProductId();
		// 상품 조회
		Product product = productRepoService.findProductById(productId);
		// 클라이언트로 부터 받은 request 추출
		ProductTextUpdateRequest request = command.getProductTextUpdateRequest();
		// 상품 엔티티에서 업데이트를 하도록 책임 전가
		product.updateTextInfo(request);
		// 업데이트.
		productRepoService.save(product);

	}
}
