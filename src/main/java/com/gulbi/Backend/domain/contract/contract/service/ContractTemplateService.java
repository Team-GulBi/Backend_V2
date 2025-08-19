package com.gulbi.Backend.domain.contract.contract.service;

import org.springframework.stereotype.Service;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateResponse;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateCommand;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateRequest;
import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractTemplateService {
	private final ContractTemplateRepoService contractTemplateRepoService;
	private final UserService userService;
	public void updateTemplate(TemplateUpdateCommand command){
		Long templateId=command.getTemplateId();
		TemplateUpdateRequest request = command.getRequest();
		ContractTemplate template = contractTemplateRepoService.findById(templateId);
		template.update(request);
		contractTemplateRepoService.save(template);
	}

	public TemplateResponse getProductContractTemplate(Long productId){
		//상품아이디에 맞는 템플릿 조회
		ContractTemplate template = contractTemplateRepoService.findByProductIdWithProduct(productId);
		//상품 이름 추출을 위한 상품 조회
		Product product = template.getProduct();
		// 빌린사람 추출
		User lender = product.getUser();
		// 빌리는 사람 추출
		User borrower = userService.getAuthenticatedUser();

		return TemplateResponse.fromEntity(template, lender, borrower, product);
	}



}
