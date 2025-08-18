package com.gulbi.Backend.domain.contract.contract.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TemplateUpdateRequest {
	@Schema(description = "상품의 규격을 입력해주세요", example = "15cm * 15cm")
	private String specification;
	@Schema(description = "상품의 상태를 적어주세요", example = "불에타고 물에 잠기고 번개맞고 변기에 빠뜨렸고 강아지가 오줌싸고감")
	private String condition;
	@Schema(description = "계약서에 추가적인 제약 혹은, 비고를 적어주세요", example = "1초 늦을때마다 꿀밤 한대 적립 반박시 너말이 틀림")
	private String note;
	@Schema(description = "대여장소", example = "정보기술대 1층 남자화장실 2번째 칸")
	private String rentalPlace;
	@Schema(description = "대여장소", example = "정보기술대 2층 남자화장실 3번째 칸")
	private String returnPlace;

	@NotNull
	@DecimalMin(value = "0.00", inclusive = true, message = "연체 이자율은 0 이상이어야 합니다.")
	@DecimalMax(value = "100.00", inclusive = true, message = "연체 이자율은 100 이하이어야 합니다.")
	private BigDecimal lateInterestRate;

	@NotNull
	@DecimalMin(value = "0.00", inclusive = true)
	@DecimalMax(value = "100.00", inclusive = true)
	private BigDecimal latePenaltyRate;

	@NotNull
	@DecimalMin(value = "0.00", inclusive = true)
	@DecimalMax(value = "100.00", inclusive = true)
	private BigDecimal damageCompensationRate;
}
