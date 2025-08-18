package com.gulbi.Backend.domain.contract.contract.controller;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateResponse;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateCommand;
import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateRequest;
import com.gulbi.Backend.domain.contract.contract.service.ContractTemplateService;
import com.gulbi.Backend.global.response.RestApiResponse;
import com.gulbi.Backend.domain.contract.contract.code.ContractSuccessCode;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ContractTemplateController {

	private final ContractTemplateService contractTemplateService;

	@GetMapping("/products/{productId}/template")
	public ResponseEntity<RestApiResponse> getProductContractTemplate(
		@PathVariable Long productId) {
		TemplateResponse data = contractTemplateService.getProductContractTemplate(productId);
		RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_FOUNDED_SUCCESS,data);
		return ResponseEntity.ok(response);
	}

	@PatchMapping("/templates/{templateId}")
	public ResponseEntity<RestApiResponse> updateContractTemplate(
		@PathVariable Long templateId,
		@RequestBody TemplateUpdateRequest request) {
		TemplateUpdateCommand command = new TemplateUpdateCommand(request, templateId);
		contractTemplateService.updateTemplate(command);
		RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_FOUNDED_SUCCESS);
		return ResponseEntity.ok(response);
	}
}
