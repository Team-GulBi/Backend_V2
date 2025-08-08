package com.gulbi.Backend.domain.contract.contract.controller;

import com.gulbi.Backend.domain.contract.contract.code.ContractSuccessCode;
import com.gulbi.Backend.domain.contract.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.contract.contract.dto.ContractResponseDto;
import com.gulbi.Backend.domain.contract.contract.dto.LenderApprovalCommand;
import com.gulbi.Backend.domain.contract.contract.service.ContractService;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.global.response.RestApiResponse;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/application/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    @PostMapping(path = "/{productId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestApiResponse> createContract(
        @PathVariable Long productId,
        @Parameter(description = "계약서") @RequestPart ContractCreateRequest contractCreateRequest,
        @Parameter(description = "예약") @RequestPart ApplicationCreateRequest applicationCreateRequest) {

        contractService.createContract(productId, contractCreateRequest, applicationCreateRequest);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_CREATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<RestApiResponse> getContractByApplication(@PathVariable("applicationId")Long applicationId){
        ContractResponseDto data = contractService.getContractByApplicationId(applicationId);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_FOUNDED_SUCCESS,data);
        return ResponseEntity.ok(response);
    }

    // 대여인 승인 상태 변경
    @PutMapping(value = "/{contractId}/lender-approval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestApiResponse> updateLenderApproval(
        @PathVariable Long contractId,
        @RequestPart MultipartFile finalContract) {
        LenderApprovalCommand command = new LenderApprovalCommand(finalContract,contractId);
        contractService.updateLenderApproval(command);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_APPROVAL_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{contractId}/lender-approval")
    public ResponseEntity<RestApiResponse> deleteContract(
        @PathVariable("contractId") Long contractId
    ){
        contractService.rejectContraction(contractId);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_UNAPPROVAL_SUCCESS);
        return ResponseEntity.ok(response);
    }

}
