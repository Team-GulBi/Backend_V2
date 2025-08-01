package com.gulbi.Backend.domain.rental.application.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ApplicationCreateDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
