package com.gulbi.Backend.domain.contract.application.dto;

import java.time.LocalDate;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ApplicationCreateRequest {

    @Schema(description = "대여 시작일 (시간까지 포함, 분/초는 00)", example = "2025-08-10T14:00:00")
    private LocalDateTime startDate;

    @Schema(description = "대여 종료일 (시간까지 포함, 분/초는 00)", example = "2025-08-15T18:00:00")
    private LocalDateTime endDate;
}
