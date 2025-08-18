package com.gulbi.Backend.domain.contract.contract.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.gulbi.Backend.domain.contract.application.repository.ApplicationRepoService;
import com.gulbi.Backend.domain.contract.contract.code.ContractErrorCode;
import com.gulbi.Backend.domain.contract.contract.dto.ContractResponse;
import com.gulbi.Backend.domain.contract.contract.dto.LenderApprovalCommand;
import com.gulbi.Backend.domain.contract.contract.entity.Contract;
import com.gulbi.Backend.domain.contract.contract.entity.ContractFactory;
import com.gulbi.Backend.domain.contract.contract.exception.ContractException;
import com.gulbi.Backend.domain.contract.contract.repository.ContractRepoService;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.rental.product.vo.ImageUrl;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepoService;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.InfraErrorCode;
import com.gulbi.Backend.global.error.S3BucketException;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.global.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final String className = this.getClass().getName();

    private final ApplicationRepoService applicationRepoService;
    private final ContractRepoService contractRepoService;
    private final UserRepoService userRepoService;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;

    // 계약서 생성
    public void createContractFromApplication(Application application) {
        // Contract 생성
        Contract contract = ContractFactory.createContract(application);
        contractRepoService.save(contract);
    }

    public ContractResponse getContractByApplicationId(Long applicationId){
        Contract contract = contractRepoService.findByApplicationId(applicationId);
        return ContractResponse.from(contract);

    }


    // 대여인 승인 상태 변경
    public void updateLenderApproval(LenderApprovalCommand command) {
        // 최종 계약서 s3업로드
            // 현재 유저가 계약의 lender인지 확인 유효성 검사
        Long contractId = command.getContractId();
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepoService.findById(contractId);
            //유효성 검증
        validateLenderPermission(contract, currentUser);
            //S3 업로드
        ImageUrl contractImage=uploadContractFile(command);
            // contract 상태변경(Lender Approval => true, Contract URL)
        contract.approveByLender(contractImage);
        contractRepoService.save(contract);

            //Application 상태 변경(Reserving -> USING)
        Application application = contract.getApplication();
        application.markAsUsing();// 상태 업데이트
        applicationRepoService.save(application);

    }

    public void rejectContraction(Long contractId){
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepoService.findById(contractId);
        //유효성 검증
        validateLenderPermission(contract, currentUser);
        //계약서 폐기
        contractRepoService.deleteById(contractId);
        //Application 상태변경
        Application application = contract.getApplication();
        application.markAsRejected();
        applicationRepoService.save(application);
    }

    // --------------------------- 유틸 메서드 ----------------------------------------------

    // 계약서 파일 업로드
    private ImageUrl uploadContractFile(LenderApprovalCommand command) {
        try {
            ImageUrl contractImage = ImageUrl.of(s3Uploader.uploadFile(command.getFinalContractImage(), "contracts"));
            return contractImage;
        }catch (IOException e){
            throw new S3BucketException(ExceptionMetaDataFactory.of(command, className,e, InfraErrorCode.S3_BUCKET_EXCEPTION));
        }
	}



    private User getAuthenticatedUser() {
        Long userId = jwtUtil.extractUserIdFromRequest();
        return userRepoService.findById(userId);

    }

    // --------------------------- 예외 메서드 ----------------------------------------------

    private void validateLenderPermission(Contract contract, User currentUser){
        if (!contract.getLender().getId().equals(currentUser.getId())) {
            Map<String,Object> args = new HashMap<>();
            args.put("contract", contract);
            args.put("currentUser", currentUser);
            throw new ContractException(ExceptionMetaDataFactory.of(args, className, null, ContractErrorCode.CURRENT_USER_NOT_PERMISSION));
        }
    }

}
