package com.gulbi.Backend.domain.rental.recommandation.service.query;

import lombok.Getter;

@Getter
public enum JsonPathQuery {
	EXTRACT_PRODUCT_IDS("$.data.result[*].metric.productId"),
	EXTRACT_BIG_CATEGORY_IDS("$.data.result[*].metric.bCategoryId"),
	EXTRACT_MIDDLE_CATEGORY_IDS("$.data.result[*].metric.mCategoryId"),
	TESt_QUERY("$.data.result[*].metric"), // 전체 쿼리 결과 수 를 얻기 위함
	TEST_QUERY_2("$.data.result[%d].metric.bCategoryId"), // 대 중 소를 각각 얻기
	TEST_QUERY_3("$.data.result[%d].metric.mCategoryId"),
	TEST_QUERY_4("$.data.result[%d].value[%d]"), // 우선 순위 값 추출

	EXTRACT_PRIORITY ("$.data.result[*].value[1]");

	private final String query;

	JsonPathQuery(String query) {
		this.query = query;
	}
}
