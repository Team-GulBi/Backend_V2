package com.gulbi.Backend.domain.rental.recommandation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class CategoryPair {
	private final Long bigCategoryId;
	private final Long midCategoryId;
}
