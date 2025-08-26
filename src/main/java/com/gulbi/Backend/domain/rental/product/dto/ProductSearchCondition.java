package com.gulbi.Backend.domain.rental.product.dto;

import com.gulbi.Backend.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// 상품검색시 QueryDSL의 where절에 들어갈 조건들
public class ProductSearchCondition {
	private String title;
	private User user;
	private Long bCategoryId;
	private Long mCategoryId;
	private Long sCategoryId;
}
