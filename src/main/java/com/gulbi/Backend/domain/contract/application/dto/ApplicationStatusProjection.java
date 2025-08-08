package com.gulbi.Backend.domain.contract.application.dto;

import java.time.LocalDate;

public interface ApplicationStatusProjection {
	LocalDate getReservationDate();
	Integer getHasReserving();
	Integer getHasReservingOrUsing();
}
