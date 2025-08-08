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
    // 예약정보로 계약 조회 ToDo:
    @GetMapping("/applications/{applicationId}")
    public ResponseEntity<RestApiResponse> getContractByApplication(@PathVariable("applicationId")Long applicationId){
        ContractResponseDto data = contractService.getContractByApplicationId(applicationId);
        //ToDo: 응답 코드 알맞게 수정
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_CREATE_SUCCESS,data);
        return ResponseEntity.ok(response);
    }

    // 대여인 승인 상태 변경
    @PutMapping(value = "/{contractId}/lender-approval", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestApiResponse> updateLenderApproval(
        @PathVariable Long contractId,
        @RequestPart MultipartFile finalContract) {
        LenderApprovalCommand command = new LenderApprovalCommand(finalContract,contractId);
        contractService.updateLenderApproval(command);
        RestApiResponse response = new RestApiResponse(ContractSuccessCode.CONTRACT_CREATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{contractId}/lender-approval")
    public void deleteContract(
        @PathVariable("contractId") Long contractId
    ){
        contractService.rejectContraction(contractId);
    }

    // // 특정 계약 조회 (상세 정보)
    // @GetMapping("/{contractId}")
    // public ResponseEntity<ContractResponseDto> getContractById(@PathVariable Long contractId) {
    //     Optional<ContractResponseDto> contract = contractService.getContractById(contractId);
    //     return contract.map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

   // // 특정 대여인의 계약 조회
   // @GetMapping("/lender/{lenderId}")
   // public ResponseEntity<List<ContractSummaryDto>> getContractsByLender(@PathVariable Long lenderId) {
   //     return ResponseEntity.ok(contractService.getContractsByLender(lenderId));
   // }
//
//    // 특정 차용인의 계약 조회
//    @GetMapping("/borrower/{borrowerId}")
//    public ResponseEntity<List<ContractSummaryDto>> getContractsByBorrower(@PathVariable Long borrowerId) {
//        return ResponseEntity.ok(contractService.getContractsByBorrower(borrowerId));
//    }

    // // 특정 신청과 관련된 계약 조회
    // @GetMapping
    // public ResponseEntity<List<ContractSummaryDto>> getContractsByApplication(@PathVariable Long applicationId) {
    //     return ResponseEntity.ok(contractService.getContractsByApplication(applicationId));
    // }
    //


    // // 차용인 승인 상태 변경
    // @PutMapping("/{contractId}/borrower-approval")
    // public ResponseEntity<ContractResponseDto> updateBorrowerApproval(
    //         @PathVariable Long contractId) {
    //     return ResponseEntity.ok(contractService.updateBorrowerApproval(contractId));
    // }

    // // 계약 파일 업로드 및 url 업데이트
    // @PostMapping(value = "/{contractId}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<ContractResponseDto> uploadContractFile(
    //         @PathVariable Long contractId,
    //         @RequestParam("file") MultipartFile file) throws IOException {
    //     return ResponseEntity.ok(contractService.uploadContractFile(contractId, file));
    // }


}
