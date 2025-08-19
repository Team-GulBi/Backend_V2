package com.gulbi.Backend.domain.contract.contract.entity;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateCreateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Product;

public class ContractTemplateFactory {
	public static ContractTemplate createTemplate(TemplateCreateRequest request, Product product){
		return ContractTemplate.builder()
			.condition(request.getCondition())
			.note(request.getNote())
			.damageCompensationRate(request.getDamageCompensationRate())
			.lateInterestRate(request.getLateInterestRate())
			.latePenaltyRate(request.getLatePenaltyRate())
			.rentalPlace(request.getRentalPlace())
			.returnPlace(request.getReturnPlace())
			.specification(request.getSpecification())
			.product(product)
			.build();
	}
}
