package com.gulbi.Backend.domain.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContractCreateRequest {
    private String itemName;              // 물품명
    private String specifications;        // 형식 및 규격
    private Integer quantity;             // 수량
    private String condition;             // 상태
    private String notes;                 // 비고
    private LocalDateTime rentalEndDate;  // 대여 종료일
    private String rentalPlace;           // 대여 장소
    private String rentalDetailAddress;   // 대여 상세 주소
    private LocalDateTime returnDate;     // 반납 날짜
    private String returnPlace;           // 반납 장소
    private String returnDetailAddress;   // 반납 상세 주소
    private BigDecimal rentalFee;         // 대여 요금
    private LocalDateTime paymentDate;    // 결제일
    private BigDecimal lateInterestRate;  // 연체 이자율
    private BigDecimal latePenaltyRate;   // 연체 벌금율
    private BigDecimal damageCompensationRate; // 손상 보상율
    private String url;                   // 계약 관련 URL (선택적)
}
