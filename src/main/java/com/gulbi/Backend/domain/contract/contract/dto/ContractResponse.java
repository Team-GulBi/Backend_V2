package com.gulbi.Backend.domain.contract.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.gulbi.Backend.domain.contract.contract.entity.Contract;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ContractResponse {
    private Long id;
    private String itemName;
    private String specifications;
    private Integer quantity;
    private String condition;
    private String notes;
    private LocalDateTime rentalEndDate;
    private String rentalPlace;
    private String rentalDetailAddress;
    private LocalDateTime returnDate;
    private String returnPlace;
    private String returnDetailAddress;
    private BigDecimal rentalFee;
    private LocalDateTime paymentDate;
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
    public ContractResponse(Long id, String itemName, String specifications, Integer quantity, String condition,
        String notes, LocalDateTime rentalEndDate, String rentalPlace, String rentalDetailAddress,
        LocalDateTime returnDate,
        String returnPlace, String returnDetailAddress, BigDecimal rentalFee, LocalDateTime paymentDate,
        BigDecimal lateInterestRate, BigDecimal latePenaltyRate, BigDecimal damageCompensationRate,
        Boolean lenderApproval,
        Boolean borrowerApproval, String url, String lenderSignature, String borrowerSignature, String lenderName, String borrowerName) {
        this.id = id;
        this.itemName = itemName;
        this.specifications = specifications;
        this.quantity = quantity;
        this.condition = condition;
        this.notes = notes;
        this.rentalEndDate = rentalEndDate;
        this.rentalPlace = rentalPlace;
        this.rentalDetailAddress = rentalDetailAddress;
        this.returnDate = returnDate;
        this.returnPlace = returnPlace;
        this.returnDetailAddress = returnDetailAddress;
        this.rentalFee = rentalFee;
        this.paymentDate = paymentDate;
        this.lateInterestRate = lateInterestRate;
        this.latePenaltyRate = latePenaltyRate;
        this.damageCompensationRate = damageCompensationRate;
        this.lenderApproval = lenderApproval;
        this.borrowerApproval = borrowerApproval;
        this.url = url;
        this.lenderSignature = lenderSignature;
        this.borrowerSignature = borrowerSignature;
        this.lenderName=lenderName;
        this.borrowerName=borrowerName;
    }

    @Override
    public String toString() {
        return "ContractResponse{" +
            "id=" + id +
            ", itemName='" + itemName + '\'' +
            ", specifications='" + specifications + '\'' +
            ", quantity=" + quantity +
            ", condition='" + condition + '\'' +
            ", notes='" + notes + '\'' +
            ", rentalEndDate=" + rentalEndDate +
            ", rentalPlace='" + rentalPlace + '\'' +
            ", rentalDetailAddress='" + rentalDetailAddress + '\'' +
            ", returnDate=" + returnDate +
            ", returnPlace='" + returnPlace + '\'' +
            ", returnDetailAddress='" + returnDetailAddress + '\'' +
            ", rentalFee=" + rentalFee +
            ", paymentDate=" + paymentDate +
            ", lateInterestRate=" + lateInterestRate +
            ", latePenaltyRate=" + latePenaltyRate +
            ", damageCompensationRate=" + damageCompensationRate +
            ", lenderApproval=" + lenderApproval +
            ", borrowerApproval=" + borrowerApproval +
            ", url='" + url + '\'' +
            ", lenderSignature='" + lenderSignature + '\'' +
            ", borrowerSignature='" + borrowerSignature + '\'' +
            '}';
    }

    public static ContractResponse from(Contract contract) {
        return new ContractResponse(
            contract.getId(),
            contract.getItemName(),
            contract.getSpecifications(),
            contract.getQuantity(),
            contract.getCondition(),
            contract.getNotes(),
            contract.getRentalEndDate(),
            contract.getRentalPlace(),
            contract.getRentalDetailAddress(),
            contract.getReturnDate(),
            contract.getReturnPlace(),
            contract.getReturnDetailAddress(),
            contract.getRentalFee(),
            contract.getPaymentDate(),
            contract.getLateInterestRate(),
            contract.getLatePenaltyRate(),
            contract.getDamageCompensationRate(),
            contract.getLenderApproval(),
            contract.getBorrowerApproval(),
            contract.getUrl(),
            contract.getLender().getProfile().getSignature(),
            contract.getBorrower().getProfile().getSignature(),
            contract.getLender().getNickname(),
            contract.getBorrower().getNickname()
        );
    }


}
