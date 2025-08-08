package com.gulbi.Backend.domain.contract.contract.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
public class ContractCreateRequest {

    @Schema(description = "물품명", example = "삼각대")
    private String itemName;

    @Schema(description = "형식 및 규격", example = "알루미늄, 150cm")
    private String specifications;

    @Schema(description = "수량", example = "2")
    private Integer quantity;

    @Schema(description = "물품 상태", example = "사용감 있음")
    private String condition;

    @Schema(description = "비고", example = "파손 주의")
    private String notes;

    @Schema(description = "대여 종료일시 (시간까지만 포함, 분/초는 00)", example = "2025-08-15T18:00:00")
    private LocalDateTime rentalEndDate;

    @Schema(description = "대여 장소", example = "서울 강남구 역삼동")
    private String rentalPlace;

    @Schema(description = "대여 상세 주소", example = "테헤란로 123, 3층")
    private String rentalDetailAddress;

    @Schema(description = "반납 일시 (시간까지만 포함, 분/초는 00)", example = "2025-08-20T18:00:00")
    private LocalDateTime returnDate;

    @Schema(description = "반납 장소", example = "서울 강남구 역삼동")
    private String returnPlace;

    @Schema(description = "반납 상세 주소", example = "테헤란로 123, 1층")
    private String returnDetailAddress;

    @Schema(description = "대여 요금", example = "50000")
    private BigDecimal rentalFee;

    @Schema(description = "결제일시 (시간까지만 포함, 분/초는 00)", example = "2025-08-10T12:00:00")
    private LocalDateTime paymentDate;

    @Schema(description = "연체 이자율", example = "0.015")
    private BigDecimal lateInterestRate;

    @Schema(description = "연체 벌금율", example = "0.03")
    private BigDecimal latePenaltyRate;

    @Schema(description = "손상 보상율", example = "0.5")
    private BigDecimal damageCompensationRate;

    @Schema(description = "계약 관련 URL (선택)", example = "https://example.com/contract/1")
    private String url;
}
