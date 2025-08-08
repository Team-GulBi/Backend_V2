package com.gulbi.Backend.domain.contract.application.entity;

public enum ApplicationStatus {
    RESERVING,  // 예약 중
    USING,      // 사용 중
    REJECTED,   // 거절됨
    RETURNED    // 반환됨
}
