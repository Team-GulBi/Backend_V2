package com.gulbi.Backend.global;

import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PersonalCursorPageable {
	private final List<PersonalCursorRequest> personalCursorRequests;
	private final Pageable pageable;

	public int size(){
		return personalCursorRequests.size();
	}
}
