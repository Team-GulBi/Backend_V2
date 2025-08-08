package com.gulbi.Backend.domain.contract.contract.entity;

import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.user.entity.User;

public class ContractFactory {
public static Contract createContract(ContractCreateRequest request, Application application, User lender, User borrower){
	return Contract.builder()
		.lender(lender)
		.borrower(borrower)
		.application(application)
		.itemName(request.getItemName())
		.specifications(request.getSpecifications())
		.quantity(request.getQuantity())
		.condition(request.getCondition())
		.notes(request.getNotes())
		.rentalEndDate(request.getRentalEndDate())
		.rentalPlace(request.getRentalPlace())
		.rentalDetailAddress(request.getRentalDetailAddress())
		.returnDate(request.getReturnDate())
		.returnPlace(request.getReturnPlace())
		.returnDetailAddress(request.getReturnDetailAddress())
		.rentalFee(request.getRentalFee())
		.paymentDate(request.getPaymentDate())
		.lateInterestRate(request.getLateInterestRate())
		.latePenaltyRate(request.getLatePenaltyRate())
		.damageCompensationRate(request.getDamageCompensationRate())
		.url(request.getUrl())
		.lenderApproval(false)  // 기본값 false
		.borrowerApproval(true)  // 기본값 true, 해당 API생성 시점에 동의한걸로 간주함.
		.build();

}
}
