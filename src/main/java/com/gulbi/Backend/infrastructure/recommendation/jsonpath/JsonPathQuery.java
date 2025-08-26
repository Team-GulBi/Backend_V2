package com.gulbi.Backend.infrastructure.recommendation.jsonpath;

import lombok.Getter;

@Getter
public enum JsonPathQuery {
	EXTRACT_PRODUCT_IDS("$.data.result[*].metric.productId"),
	EXTRACT_BIG_CATEGORY_IDS("$.data.result[*].metric.bCategoryId"),
	EXTRACT_MIDDLE_CATEGORY_IDS("$.data.result[*].metric.mCategoryId"),
	EXTRACT_TOTAL_RESULT_COUNT("$.data.result[*].metric"), // 전체 쿼리 결과 수 를 얻기 위함
	EXTRACT_BIG_CATEGORY_ID("$.data.result[%d].metric.bCategoryId"), // 대 중 소를 각각 얻기
	EXTRACT_MIDDLE_CATEGORY_ID("$.data.result[%d].metric.mCategoryId"),
	EXTRACT_TOTAL_VIEWED("$.data.result[%d].value[%d]"); // 우선 순위 값(조회 된 수) 추출

	private final String query;

	JsonPathQuery(String query) {
		this.query = query;
	}
}
