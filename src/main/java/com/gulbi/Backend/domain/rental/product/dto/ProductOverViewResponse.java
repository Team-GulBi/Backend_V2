package com.gulbi.Backend.domain.rental.product.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOverViewResponse {

	private Long id;
	private String mainImage;
	private String title;
	private int price;
	private LocalDateTime createdAt;

}
