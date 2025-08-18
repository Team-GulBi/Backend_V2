package com.gulbi.Backend.domain.contract.contract.entity;

import java.math.BigDecimal;

import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.user.entity.User;

public class ContractFactory {
public static Contract createContract(Application application){
	Product product = application.getProduct();
	User borrower = application.getUser();
	User lender = product.getUser();
	ContractTemplate template = product.getContractTemplate();
	return Contract.builder()
		.lender(lender)
		.borrower(borrower)
		.application(application)
		.itemName(product.getName())
		.specifications(template.getSpecification())
		.quantity(1)
		.condition(template.getCondition())
		.notes(template.getNote())
		.rentalPlace(template.getRentalPlace())
		.returnPlace(template.getReturnPlace())
		.rentalFee(BigDecimal.valueOf(product.getPrice()))
		.lateInterestRate(template.getLateInterestRate())
		.latePenaltyRate(template.getLatePenaltyRate())
		.damageCompensationRate(template.getDamageCompensationRate())
		.url(null)
		.lenderApproval(false)  // 기본값 false
		.borrowerApproval(true)  // 기본값 true, 해당 API생성 시점에 Borrower는 동의한걸로 간주함.
		.build();

}
}
