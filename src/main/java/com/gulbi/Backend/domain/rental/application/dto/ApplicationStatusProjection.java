package com.gulbi.Backend.domain.rental.application.dto;

import java.time.LocalDate;

public interface ApplicationStatusProjection {
	LocalDate getReservationDate();
	Integer getHasReserving();
	Integer getHasReservingOrUsing();
}
