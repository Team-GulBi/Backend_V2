package com.gulbi.Backend.domain.contract.application.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;

@Getter
public class ApplicationStatusResponse {
	private LocalDate reservationDate;



	private Boolean HasReserving;
	private Boolean HasReservingOrUsing;

	public ApplicationStatusResponse() {
	}
	public ApplicationStatusResponse(LocalDate reservationDate, Integer hasReserving, Integer hasReservingOrUsing) {
		this.reservationDate = reservationDate;
		HasReserving = parse(hasReserving);
		HasReservingOrUsing = parse(hasReservingOrUsing);
	}

	@Override
	public String toString() {
		return "ApplicationStatusResponse{" +
			"reservationDate=" + reservationDate +
			", HasReserving=" + HasReserving +
			", HasReservingOrUsing=" + HasReservingOrUsing +
			'}';
	}

	private Boolean parse(Integer result){
		if(result == 1){
			return true;
		}
		return false;
	}

	public static List<ApplicationStatusResponse> from(List<ApplicationStatusProjection> input){
		return input.stream()
			.map(item -> new ApplicationStatusResponse(
				item.getReservationDate(),
				item.getHasReserving(),
				item.getHasReservingOrUsing()
			))
			.toList();
	}

}
