package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;

@Component
public class ProductCategoryUpdateStrategy implements ProductUpdateStrategy{
	private final CategoryService categoryService;
	private final ProductRepoService productRepoService;

	public ProductCategoryUpdateStrategy(CategoryService categoryService,
		ProductRepoService productRepoService) {
		this.categoryService = categoryService;
		this.productRepoService = productRepoService;
	}

	@Override
	public boolean canUpdate(ProductContentUpdateCommand command) {
		return command.getProductCategoryUpdateRequest() != null;
	}

	@Override
	public void update(ProductContentUpdateCommand command) {
		//카테고리 유효성 조회
		ProductCategoryUpdateRequest request = command.getProductCategoryUpdateRequest();
		CategoryBundle bundle = validateCategories(request);
		// 상품 유효성 검사
		Long productId = command.getProductId();
		Product product = productRepoService.findProductById(productId);
		// 업데이트
		product.updateCategories(bundle);
		productRepoService.save(product);
	}

	//카테고리 유효성 검사.
	private CategoryBundle validateCategories(ProductCategoryUpdateRequest request){
		return categoryService.resolveCategories(request);
	}
}
