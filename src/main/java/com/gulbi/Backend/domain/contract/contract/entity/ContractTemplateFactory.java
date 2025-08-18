package com.gulbi.Backend.domain.contract.contract.entity;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateCreateRequest;

public class ContractTemplateFactory {
	public static ContractTemplate createTemplate(TemplateCreateRequest request){
		return ContractTemplate.builder()
			.condition(request.getCondition())
			.note(request.getNote())
			.damageCompensationRate(request.getDamageCompensationRate())
			.lateInterestRate(request.getLateInterestRate())
			.latePenaltyRate(request.getLatePenaltyRate())
			.rentalPlace(request.getRentalPlace())
			.returnPlace(request.getReturnPlace())
			.specification(request.getSpecification())
			.build();
	}
}
