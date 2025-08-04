package com.gulbi.Backend.domain.contract.controller;

import com.gulbi.Backend.domain.contract.code.ContractSuccessCode;
import com.gulbi.Backend.domain.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.contract.dto.ContractResponseDto;
import com.gulbi.Backend.domain.contract.dto.ContractSummaryDto;
import com.gulbi.Backend.domain.contract.service.ContractService;
import com.gulbi.Backend.global.response.RestApiResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/application/{applicationId}/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;

    @PostMapping
    public ResponseEntity<RestApiResponse> createContract(
            @PathVariable Long applicationId,
            @RequestBody ContractCreateRequest contractCreateRequest) {

        contractService.createContract(applicationId, contractCreateRequest);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_CREATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    // 특정 계약 조회 (상세 정보)
    @GetMapping("/{contractId}")
    public ResponseEntity<ContractResponseDto> getContractById(@PathVariable Long contractId) {
        Optional<ContractResponseDto> contract = contractService.getContractById(contractId);
        return contract.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
//
//    // 특정 대여인의 계약 조회
//    @GetMapping("/lender/{lenderId}")
//    public ResponseEntity<List<ContractSummaryDto>> getContractsByLender(@PathVariable Long lenderId) {
//        return ResponseEntity.ok(contractService.getContractsByLender(lenderId));
//    }
//
//    // 특정 차용인의 계약 조회
//    @GetMapping("/borrower/{borrowerId}")
//    public ResponseEntity<List<ContractSummaryDto>> getContractsByBorrower(@PathVariable Long borrowerId) {
//        return ResponseEntity.ok(contractService.getContractsByBorrower(borrowerId));
//    }

    // 특정 신청과 관련된 계약 조회
    @GetMapping
    public ResponseEntity<List<ContractSummaryDto>> getContractsByApplication(@PathVariable Long applicationId) {
        return ResponseEntity.ok(contractService.getContractsByApplication(applicationId));
    }

    // 대여인 승인 상태 변경
    @PutMapping("/{contractId}/lender-approval")
    public ResponseEntity<ContractResponseDto> updateLenderApproval(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.updateLenderApproval(contractId));
    }

    // 차용인 승인 상태 변경
    @PutMapping("/{contractId}/borrower-approval")
    public ResponseEntity<ContractResponseDto> updateBorrowerApproval(
            @PathVariable Long contractId) {
        return ResponseEntity.ok(contractService.updateBorrowerApproval(contractId));
    }

    // 계약 파일 업로드 및 url 업데이트
    @PostMapping(value = "/{contractId}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ContractResponseDto> uploadContractFile(
            @PathVariable Long contractId,
            @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.ok(contractService.uploadContractFile(contractId, file));
    }


}
