package com.gulbi.Backend.global;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PersonalCursorRequest {
	private final Long bCategoryId;
	private final Long mCategoryId;
	private final Long lastProductId;
	private final LocalDateTime lastCreatedAt;
	private final int priority;
}
