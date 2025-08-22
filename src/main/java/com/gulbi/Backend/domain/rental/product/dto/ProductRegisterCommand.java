package com.gulbi.Backend.domain.rental.product.dto;

import org.springframework.lang.Nullable;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateCreateRequest;
import com.gulbi.Backend.domain.rental.product.vo.ProductImageFiles;

import lombok.Getter;

@Getter
public class ProductRegisterCommand {
	private ProductRegisterRequest productRegisterRequest;
	private TemplateCreateRequest templateCreateRequest;
	private ProductImageFiles imageFiles;
	private ProductImageFiles mainImageFiles;

	public ProductRegisterCommand(ProductRegisterRequest productRegisterRequest,
		TemplateCreateRequest templateCreateRequest, ProductImageFiles imageFiles, ProductImageFiles mainImageFiles) {
		this.productRegisterRequest = productRegisterRequest;
		this.templateCreateRequest = templateCreateRequest;
		this.imageFiles = imageFiles;
		this.mainImageFiles = mainImageFiles;
	}
}
