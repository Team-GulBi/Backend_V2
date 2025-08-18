package com.gulbi.Backend.domain.contract.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gulbi.Backend.domain.contract.contract.entity.Contract;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContractResponse {
    private Long id;

    private String itemName;
    private String specifications;
    private Integer quantity;
    private String condition;
    private String notes;

    private LocalDateTime rentalStartDate;
    private LocalDateTime rentalEndDate;
    private String rentalPlace;
    private String returnPlace;

    private BigDecimal rentalFee;

    private BigDecimal lateInterestRate;
    private BigDecimal latePenaltyRate;
    private BigDecimal damageCompensationRate;

    private Boolean lenderApproval;
    private Boolean borrowerApproval;

    private String url;

    private String lenderSignature;
    private String borrowerSignature;

    private String lenderName;
    private String borrowerName;

    public static ContractResponse from(Contract contract) {
        return ContractResponse.builder()
            .id(contract.getId())
            .itemName(contract.getItemName())
            .specifications(contract.getSpecifications())
            .quantity(contract.getQuantity())
            .condition(contract.getCondition())
            .notes(contract.getNotes())
            .rentalStartDate(contract.getApplication().getStartDate())
            .rentalEndDate(contract.getApplication().getEndDate())
            .rentalPlace(contract.getRentalPlace())
            .returnPlace(contract.getReturnPlace())
            .rentalFee(contract.getRentalFee())
            .lateInterestRate(contract.getLateInterestRate())
            .latePenaltyRate(contract.getLatePenaltyRate())
            .damageCompensationRate(contract.getDamageCompensationRate())
            .lenderApproval(contract.getLenderApproval())
            .borrowerApproval(contract.getBorrowerApproval())
            .url(contract.getUrl())
            .lenderSignature(contract.getLender().getProfile().getSignature())
            .borrowerSignature(contract.getBorrower().getProfile().getSignature())
            .lenderName(contract.getLender().getNickname())
            .borrowerName(contract.getBorrower().getNickname())
            .build();
    }
}
