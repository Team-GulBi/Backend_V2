package com.gulbi.Backend.domain.contract.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TemplateResponse {

	private Long templateId;

	private String specification;

	private String condition;

	private String note;

	private String rentalPlace;

	private String returnPlace;

	private BigDecimal lateInterestRate;

	private BigDecimal latePenaltyRate;

	private BigDecimal damageCompensationRate;

	private String lenderName;

	private String borrowerName;

	private String productName;



	public static TemplateResponse fromEntity(ContractTemplate template, User lender, User borrower, Product product){
		return TemplateResponse.builder()
			.templateId(template.getId())
			.specification(template.getSpecification())
			.condition(template.getCondition())
			.note(template.getNote())
			.rentalPlace(template.getRentalPlace())
			.returnPlace(template.getReturnPlace())
			.lateInterestRate(template.getLateInterestRate())
			.latePenaltyRate(template.getLatePenaltyRate())
			.damageCompensationRate(template.getDamageCompensationRate())
			.borrowerName(borrower.getNickname())
			.lenderName(lender.getNickname())
			.productName(product.getName())
			.build();
	}


}



