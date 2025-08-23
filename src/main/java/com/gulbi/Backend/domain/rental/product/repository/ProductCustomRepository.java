package com.gulbi.Backend.domain.rental.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.global.CursorPageable;

public interface ProductCustomRepository {
	ProductOverviewSlice findAllByCursor(CursorPageable cursorPageable);
}
