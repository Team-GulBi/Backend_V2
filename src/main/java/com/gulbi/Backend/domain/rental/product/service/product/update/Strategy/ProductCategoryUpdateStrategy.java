package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryBusinessService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;

@Component
public class ProductCategoryUpdateStrategy implements ProductUpdateStrategy{
	private final CategoryBusinessService categoryBusinessService;
	private final ProductRepoService productRepoService;

	public ProductCategoryUpdateStrategy(CategoryBusinessService categoryBusinessService,
		ProductRepoService productRepoService) {
		this.categoryBusinessService = categoryBusinessService;
		this.productRepoService = productRepoService;
	}

	@Override
	public boolean canUpdate(ProductContentUpdateCommand command) {
		return command.getProductCategoryUpdateRequest() != null;
	}

	@Override
	public void update(ProductContentUpdateCommand command) {
		validateCategories(command);
		productRepoService.updateProductCategories(command);
	}

	//카테고리 유효성 검사.
	private void validateCategories(ProductContentUpdateCommand productCategoryUpdateRequest){
		CategoryInProductDto category = categoryBusinessService.resolveCategories(productCategoryUpdateRequest.getProductCategoryUpdateRequest());
	}
}
