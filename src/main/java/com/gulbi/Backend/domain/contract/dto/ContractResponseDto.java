package com.gulbi.Backend.domain.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractResponseDto {
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
}
