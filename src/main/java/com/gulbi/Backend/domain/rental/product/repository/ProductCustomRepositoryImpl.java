package com.gulbi.Backend.domain.rental.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.entity.QProduct;
import com.gulbi.Backend.global.CursorPageable;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

	private final JPAQueryFactory queryFactory;

	@Override
	public ProductOverviewSlice findAllByCursor(CursorPageable cursorPageable) {
		// pageable 캔따기
		Pageable pageable = cursorPageable.getPageable();
		Long lastId = cursorPageable.getLastId();
		LocalDateTime lastTime = cursorPageable.getLastCreatedAt();
		//Q클래스 선언
		QProduct product = QProduct.product;
		// 요청사이즈 출력
		int limit = pageable.getPageSize();

		List<ProductOverViewResponse> results = queryFactory
			.select(Projections.fields(
				ProductOverViewResponse.class,
				product.id.as("id"),
				product.title.as("title"),
				product.price.as("price"),
				product.mainImage.as("mainImage"),
				product.createdAt.as("createdAt")
			))
			.from(product)
			.where(cursorCondition(product, lastId, lastTime))
			.orderBy(toOrderSpecifiers(pageable.getSort()))
			.limit(limit + 1) // hasNext 체크
			.fetch();
		//뒤에 있는지 함 보고
		boolean hasNext = results.size() > limit;
		//limit + 1로 확인 했으니까 보낼때는 맨 뒤 요소 하나 지워서
		if (hasNext) results.remove(limit);

		return new ProductOverviewSlice(hasNext, results);
	}

	// 커서 조건
	private BooleanExpression cursorCondition(QProduct product, Long lastId, LocalDateTime lastTime) {
		if (lastId == null || lastTime == null) return null;
		return product.createdAt.lt(lastTime)
			.or(product.createdAt.eq(lastTime).and(product.id.lt(lastId)));
	}

	// Sort -> QueryDSL OrderSpecifier 변환
	// 정렬 조건을 나타내는 객체
	private OrderSpecifier<?>[] toOrderSpecifiers(Sort sort) {
		return sort.stream()
			.map(order -> {
				// Product 엔티티를 기반으로 PathBuilder 생성
				PathBuilder<Product> path = new PathBuilder<>(Product.class, "product");

				// 정렬 방향 설정
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;

				return new OrderSpecifier(
					direction,
					path.get(order.getProperty())
				);
			}).toArray(OrderSpecifier[]::new);
	}

}


