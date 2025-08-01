package com.gulbi.Backend.domain.contract.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractSummaryDto {
    private Long id;
    private String itemName;
    private Boolean lenderApproval;
    private Boolean borrowerApproval;
    private LocalDateTime createdDate;
}
