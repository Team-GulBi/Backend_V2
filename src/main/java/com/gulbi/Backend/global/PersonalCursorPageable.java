package com.gulbi.Backend.global;

import java.util.List;

import org.springframework.data.domain.Pageable;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PersonalCursorPageable {
	private final List<CategoryCursor> categoryCursors;
	private final Pageable pageable;

	public boolean isEmptyCursor(){
		return categoryCursors.isEmpty();
	}
}
