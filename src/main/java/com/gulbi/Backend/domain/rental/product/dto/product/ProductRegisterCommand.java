package com.gulbi.Backend.domain.rental.product.dto.product;

import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageFiles;

import lombok.Getter;

@Getter
public class ProductRegisterCommand {
	private ProductRegisterRequest productRegisterRequest;
	private ProductImageFiles imageFiles;
	private ProductImageFiles mainImageFiles;

	public ProductRegisterCommand(ProductRegisterRequest productRegisterRequest, ProductImageFiles imageFiles,
		ProductImageFiles mainImageFiles) {
		this.productRegisterRequest = productRegisterRequest;
		this.imageFiles = imageFiles;
		this.mainImageFiles = mainImageFiles;
	}
}
