package com.gulbi.Backend.domain.contract.service;

import com.gulbi.Backend.domain.contract.dto.ContractCreateRequest;
import com.gulbi.Backend.domain.contract.dto.ContractResponseDto;
import com.gulbi.Backend.domain.contract.dto.ContractSummaryDto;
import com.gulbi.Backend.domain.contract.entity.Contract;
import com.gulbi.Backend.domain.contract.repository.ContractRepository;
import com.gulbi.Backend.domain.rental.application.entity.Application;
import com.gulbi.Backend.domain.rental.application.repository.ApplicationRepository;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.global.util.JwtUtil;
import com.gulbi.Backend.global.util.S3Uploader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final ContractRepository contractRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final S3Uploader s3Uploader;

    // JWT에서 현재 로그인한 유저 추출
    public User getAuthenticatedUser() {
        Long userId = jwtUtil.extractUserIdFromRequest();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));
    }

    // 계약 생성
    public void createContract(Long applicationId, ContractCreateRequest dto) {
        // 요청을 보낸 사용자 (lender)
        User lender = getAuthenticatedUser();

        // applicationId를 기반으로 Application 찾기
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("해당 신청을 찾을 수 없습니다."));

        // borrower는 Application 객체의 User
        User borrower = application.getUser();

        // Contract 생성
        Contract contract = Contract.builder()
                .lender(lender)
                .borrower(borrower)
                .application(application)
                .itemName(dto.getItemName())
                .specifications(dto.getSpecifications())
                .quantity(dto.getQuantity())
                .condition(dto.getCondition())
                .notes(dto.getNotes())
                .rentalEndDate(dto.getRentalEndDate())
                .rentalPlace(dto.getRentalPlace())
                .rentalDetailAddress(dto.getRentalDetailAddress())
                .returnDate(dto.getReturnDate())
                .returnPlace(dto.getReturnPlace())
                .returnDetailAddress(dto.getReturnDetailAddress())
                .rentalFee(dto.getRentalFee())
                .paymentDate(dto.getPaymentDate())
                .lateInterestRate(dto.getLateInterestRate())
                .latePenaltyRate(dto.getLatePenaltyRate())
                .damageCompensationRate(dto.getDamageCompensationRate())
                .url(dto.getUrl())
                .lenderApproval(false)  // 기본값 false
                .borrowerApproval(false)  // 기본값 false
                .build();

        contractRepository.save(contract);
    }

    // 대여인 승인 상태 변경
    public ContractResponseDto updateLenderApproval(Long contractId) {
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계약을 찾을 수 없습니다."));

        // 현재 유저가 계약의 lender인지 확인
        if (!contract.getLender().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "대여인만 승인 상태를 변경할 수 있습니다.");
        }

        contract.approveByLender();
        contractRepository.save(contract);

        return convertToDetailDto(contract);
    }

    // 차용인
    public ContractResponseDto updateBorrowerApproval(Long contractId) {
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계약을 찾을 수 없습니다."));

        // 현재 유저가 계약의 lender인지 확인
        if (!contract.getBorrower().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "대여인만 승인 상태를 변경할 수 있습니다.");
        }

        contract.approveByBorrower();
        contractRepository.save(contract);

        return convertToDetailDto(contract);
    }


    // 특정 계약 조회 (상세 정보)
    public Optional<ContractResponseDto> getContractById(Long contractId) {
        return contractRepository.findById(contractId)
                .map(this::convertToDetailDto);
    }

    // 특정 대여인의 계약 조회
    public List<ContractSummaryDto> getContractsByLender(Long lenderId) {
        User lender = userRepository.findById(lenderId)
                .orElseThrow(() -> new RuntimeException("대여인을 찾을 수 없습니다."));
        return contractRepository.findByLender(lender).stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    // 특정 차용인의 계약 조회
    public List<ContractSummaryDto> getContractsByBorrower(Long borrowerId) {
        User borrower = userRepository.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("차용인을 찾을 수 없습니다."));
        return contractRepository.findByBorrower(borrower).stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    // 특정 신청과 관련된 계약 조회
    public List<ContractSummaryDto> getContractsByApplication(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("해당 신청을 찾을 수 없습니다."));
        return contractRepository.findByApplication(application).stream()
                .map(this::convertToSummaryDto)
                .collect(Collectors.toList());
    }

    // 계약서 파일 업로드
    public ContractResponseDto uploadContractFile(Long contractId, MultipartFile file) throws IOException {
        User currentUser = getAuthenticatedUser();
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 계약을 찾을 수 없습니다."));

        // 계약의 대여인 또는 차용인만 업로드 가능
        if (!contract.getLender().getId().equals(currentUser.getId()) &&
                !contract.getBorrower().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 계약의 대여인 또는 차용인만 파일을 업로드할 수 있습니다.");
        }

        // 파일을 S3에 업로드
        String fileUrl = s3Uploader.uploadFile(file, "contracts");

        // Contract의 url 필드 업데이트
        contract.updateUrl(fileUrl);
        contractRepository.save(contract);

        return convertToDetailDto(contract);
    }


    // Contract → ContractSummaryDto 변환 (전체 조회용)
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

        // 대여인이 승인했으면 서명을 추가
        if (contract.getLenderApproval()) {
            dto.setLenderSignature(
                    contract.getLender().getProfile() != null ? contract.getLender().getProfile().getSignature() : null
            );
        }

        // 차용인이 승인했으면 서명을 추가
        if (contract.getBorrowerApproval()) {
            dto.setBorrowerSignature(
                    contract.getBorrower().getProfile() != null ? contract.getBorrower().getProfile().getSignature()
                            : null
            );
        }
        return dto;
    }

}
