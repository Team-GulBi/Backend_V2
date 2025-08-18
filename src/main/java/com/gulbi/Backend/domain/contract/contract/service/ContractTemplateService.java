package com.gulbi.Backend.domain.contract.contract.service;

import org.springframework.stereotype.Service;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateResponse;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateCommand;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateRequest;
import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContractTemplateService {
	private final ContractTemplateRepoService contractTemplateRepoService;

	public void updateTemplate(TemplateUpdateCommand command){
		Long templateId=command.getTemplateId();
		TemplateUpdateRequest request = command.getRequest();
		ContractTemplate template = contractTemplateRepoService.findById(templateId);
		template.update(request);
		contractTemplateRepoService.save(template);
	}

	public TemplateResponse getProductContractTemplate(Long productId){
		ContractTemplate template = contractTemplateRepoService.findByProductId(productId);
		return TemplateResponse.fromEntity(template);
	}



}
