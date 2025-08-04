package com.gulbi.Backend.domain.rental.product.service.product.update.Strategy;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.request.update.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryBusinessService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
@Component
public class ProductCategoryUpdateStrategy implements ProductUpdateStrategy{
	private final CategoryBusinessService categoryBusinessService;
	private final ProductCrudService productCrudService;

	public ProductCategoryUpdateStrategy(CategoryBusinessService categoryBusinessService,
		ProductCrudService productCrudService) {
		this.categoryBusinessService = categoryBusinessService;
		this.productCrudService = productCrudService;
	}

	@Override
	public boolean canUpdate(ProductContentUpdateCommand command) {
		return command.getProductCategoryUpdateRequest() != null;
	}

	@Override
	public void update(ProductContentUpdateCommand command) {
		validateCategories(command);
		productCrudService.updateProductCategories(command);
	}

	//카테고리 유효성 검사.
	private void validateCategories(ProductContentUpdateCommand productCategoryUpdateRequest){
		CategoryInProductDto category = categoryBusinessService.resolveCategories(productCategoryUpdateRequest.getProductCategoryUpdateRequest());
	}
}
