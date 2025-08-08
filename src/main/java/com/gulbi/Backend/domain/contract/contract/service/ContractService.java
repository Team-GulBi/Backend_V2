package com.gulbi.Backend.domain.contract.contract.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.gulbi.Backend.domain.contract.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.contract.contract.dto.ContractResponseDto;
import com.gulbi.Backend.domain.contract.contract.dto.ContractSummaryDto;
import com.gulbi.Backend.domain.contract.contract.dto.LenderApprovalCommand;
import com.gulbi.Backend.domain.contract.contract.entity.Contract;
import com.gulbi.Backend.domain.contract.contract.repository.ContractRepoService;
import com.gulbi.Backend.domain.contract.contract.repository.ContractRepository;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.application.entity.ApplicationStatus;
import com.gulbi.Backend.domain.contract.application.repository.ApplicationRepository;
import com.gulbi.Backend.domain.contract.application.service.ApplicationService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepoService;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractService {
    private final ApplicationService applicationService;
    private final ApplicationRepository applicationRepository;
    private final ContractRepoService contractRepoService;
    private final UserRepoService userRepoService;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;

    // 계약 및 예약 생성
    public void createContract(Long productId, ContractCreateRequest contractCreateRequest,ApplicationCreateRequest applicationCreateRequest) {
        // 요청을 보낸 사용자 (borrower)
        User borrower = getAuthenticatedUser();
        // ToDo: void -> Application
        Application application = applicationService.createApplication(productId, applicationCreateRequest);
        User lender = application.getProduct().getUser();

        // Contract 생성
        Contract contract = Contract.builder()
                .lender(lender)
                .borrower(borrower)
                .application(application)
                .itemName(contractCreateRequest.getItemName())
                .specifications(contractCreateRequest.getSpecifications())
                .quantity(contractCreateRequest.getQuantity())
                .condition(contractCreateRequest.getCondition())
                .notes(contractCreateRequest.getNotes())
                .rentalEndDate(contractCreateRequest.getRentalEndDate())
                .rentalPlace(contractCreateRequest.getRentalPlace())
                .rentalDetailAddress(contractCreateRequest.getRentalDetailAddress())
                .returnDate(contractCreateRequest.getReturnDate())
                .returnPlace(contractCreateRequest.getReturnPlace())
                .returnDetailAddress(contractCreateRequest.getReturnDetailAddress())
                .rentalFee(contractCreateRequest.getRentalFee())
                .paymentDate(contractCreateRequest.getPaymentDate())
                .lateInterestRate(contractCreateRequest.getLateInterestRate())
                .latePenaltyRate(contractCreateRequest.getLatePenaltyRate())
                .damageCompensationRate(contractCreateRequest.getDamageCompensationRate())
                .url(contractCreateRequest.getUrl())
                .lenderApproval(false)  // 기본값 false
                .borrowerApproval(true)  // 기본값 true, 해당 API생성 시점에 동의한걸로 간주함.
                .build();

        contractRepoService.save(contract);
    }

    public ContractResponseDto getContractByApplicationId(Long applicationId){
        //ToDo: 해당 책임은 CrudService 클래스를 만들어서 위임 예정
        Contract contract = contractRepoService.findByApplicationId(applicationId);
        return convertToDetailDto(contract);
    }


    // 대여인 승인 상태 변경
    //ToDo: contract 상태변경 + s3업로드 => 책임과 역할 분리..
    public ContractResponseDto updateLenderApproval(LenderApprovalCommand command) {

        // ToDo: s3업로드 및 상태변경
        uploadContractFile(command);
        // ToDo: contract 상태변경
        Long contractId = command.getContractId();
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepoService.findById(contractId);

        // 현재 유저가 계약의 lender인지 확인
        if (!contract.getLender().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "대여인만 승인 상태를 변경할 수 있습니다.");
        }

        contract.approveByLender();
        contractRepoService.save(contract);
        //ToDo: 해당 책임은 CrudService 클래스를 만들어서 위임 예정

        //ToDo: Application 상태 변경
        Long applicationId = contract.getApplication().getId();
        applicationRepository.updateApplcationStatus(applicationId, ApplicationStatus.USING);
        return convertToDetailDto(contract);
    }

    public void rejectContraction(Long contractId){
        Contract contract = contractRepoService.findById(contractId);
        Long applicationId = contract.getApplication().getId();
        contractRepoService.deleteById(contractId);
        applicationRepository.updateApplcationStatus(applicationId,ApplicationStatus.REJECTED);
    }

    // 계약서 파일 업로드
    //ToDo: 업로드랑 업데이트의 두가지 책임을 가지고 잇음.
    public void uploadContractFile(LenderApprovalCommand command) {
        Long contractId = command.getContractId();
        MultipartFile contractImage = command.getFinalContractImage();
        User currentUser = getAuthenticatedUser();
        try {
        //ToDo: 유효성 검사 중복 발견, 조치
        Contract contract = contractRepoService.findById(contractId);


        // 계약의 대여인 또는 차용인만 업로드 가능
        if (!contract.getLender().getId().equals(currentUser.getId()) &&
                !contract.getBorrower().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 계약의 대여인 또는 차용인만 파일을 업로드할 수 있습니다.");
        }

        // 파일을 S3에 업로드
        String fileUrl = s3Uploader.uploadFile(contractImage, "contracts");

        // Contract의 url 필드 업데이트
        contract.updateUrl(fileUrl);
            contractRepoService.save(contract);
        }catch (Exception e){
            throw new RuntimeException("임시 에러 코드 입니다, 수정 해야 합니다");
        }

    }


    // Contract → ContractSummaryDto 변환 (전체 조회용)
    // ToDo: 해당 책임은 CrudService 클래스를 만들어서 위임 예정
    private ContractSummaryDto convertToSummaryDto(Contract contract) {
        ContractSummaryDto dto = new ContractSummaryDto();
        dto.setId(contract.getId());
        dto.setItemName(contract.getItemName());
        dto.setLenderApproval(contract.getLenderApproval());
        dto.setBorrowerApproval(contract.getBorrowerApproval());
        dto.setCreatedDate(contract.getCreatedDate());
        return dto;
    }

    // Contract → ContractResponseDto 변환 (상세 조회용)
    // ToDo: 해당 책임은 CrudService 클래스를 만들어서 위임 예정
    private ContractResponseDto convertToDetailDto(Contract contract) {
        ContractResponseDto dto = new ContractResponseDto();
        dto.setId(contract.getId());
        dto.setItemName(contract.getItemName());
        dto.setSpecifications(contract.getSpecifications());
        dto.setQuantity(contract.getQuantity());
        dto.setCondition(contract.getCondition());
        dto.setNotes(contract.getNotes());
        dto.setRentalEndDate(contract.getRentalEndDate());
        dto.setRentalPlace(contract.getRentalPlace());
        dto.setRentalDetailAddress(contract.getRentalDetailAddress());
        dto.setReturnDate(contract.getReturnDate());
        dto.setReturnPlace(contract.getReturnPlace());
        dto.setReturnDetailAddress(contract.getReturnDetailAddress());
        dto.setRentalFee(contract.getRentalFee());
        dto.setPaymentDate(contract.getPaymentDate());
        dto.setLateInterestRate(contract.getLateInterestRate());
        dto.setLatePenaltyRate(contract.getLatePenaltyRate());
        dto.setDamageCompensationRate(contract.getDamageCompensationRate());
        dto.setLenderApproval(contract.getLenderApproval());
        dto.setBorrowerApproval(contract.getBorrowerApproval());
        dto.setUrl(contract.getUrl());
        //서명 추가
        dto.setLenderSignature(contract.getLender().getProfile().getSignature());
        dto.setBorrowerSignature(contract.getBorrower().getProfile().getSignature());

        return dto;
    }

    private User getAuthenticatedUser() {
        Long userId = jwtUtil.extractUserIdFromRequest();
        return userRepoService.findById(userId);

    }

}
