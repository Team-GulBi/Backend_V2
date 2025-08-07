package com.gulbi.Backend.domain.contract.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class LenderApprovalCommand {
	private MultipartFile finalContractImage;
	private Long contractId;

	public LenderApprovalCommand() {
	}

	public LenderApprovalCommand(MultipartFile finalContractImage, Long contractId) {
		this.finalContractImage = finalContractImage;
		this.contractId = contractId;
	}
}
