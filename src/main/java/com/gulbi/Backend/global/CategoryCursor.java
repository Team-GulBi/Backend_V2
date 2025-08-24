package com.gulbi.Backend.global;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CategoryCursor {
	private final Long bigCategoryId;
	private final Long midCategoryId;
	private final Long priority;
	private final Long lastProductId;
	private final LocalDateTime createdAt;
}
