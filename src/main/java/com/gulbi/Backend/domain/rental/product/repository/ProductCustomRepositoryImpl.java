package com.gulbi.Backend.domain.rental.product.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchCondition;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.entity.QProduct;
import com.gulbi.Backend.domain.user.entity.User;
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

	// WHERE절에 조건들을 동적으로 받아 쓰기위해 ProductSearchCondition객체를 받음, 대신 호출자에서는 어떤걸로 호출하는지 명확한 메서드로 호출하게 할거임
	//
	@Override
	public ProductOverviewSlice findAllByCursor(CursorPageable cursorPageable, ProductSearchCondition condition) {
		Pageable pageable = cursorPageable.getPageable();
		Long lastId = cursorPageable.getLastId();
		LocalDateTime lastTime = cursorPageable.getLastCreatedAt();

		QProduct product = QProduct.product;
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
			.where( //제목에 부합, 특정 유저 아이디, 대신 null이면 동작 안함
				cursorCondition(product, lastId, lastTime),
				titleContains(condition.getTitle()),
				userEq(condition.getUser()),
				deletedAtIsNull()
			)
			.orderBy(toOrderSpecifiers(pageable.getSort()))
			.limit(limit + 1)
			.fetch();

		boolean hasNext = results.size() > limit;
		if (hasNext) results.remove(limit);

		return new ProductOverviewSlice(hasNext, results);
	}

	// WHERE절에 조건들을 동적으로 받아 쓰도록 만든것들

		// Where절 필터 메서드들
	private BooleanExpression cursorCondition(QProduct product, Long lastId, LocalDateTime lastTime) {
		if (lastId == null || lastTime == null) return null;
		return product.createdAt.lt(lastTime)
			.or(product.createdAt.eq(lastTime).and(product.id.lt(lastId)));
	}

	private BooleanExpression titleContains(String title) {
		return (title == null || title.isBlank()) ? null : QProduct.product.title.containsIgnoreCase(title);
	}

	private BooleanExpression userEq(User user) {
		return (user == null) ? null : QProduct.product.user.id.eq(user.getId());
	}

		// 정렬기준
	private OrderSpecifier<?>[] toOrderSpecifiers(Sort sort) {
		return sort.stream()
			.map(order -> {
				PathBuilder<Product> path = new PathBuilder<>(Product.class, "product");
				Order direction = order.isAscending() ? Order.ASC : Order.DESC;
				return new OrderSpecifier<>(
					direction,
					path.getComparable(order.getProperty(), Comparable.class)
				);
			})
			.toArray(OrderSpecifier[]::new);
	}
	private BooleanExpression deletedAtIsNull() {
		return QProduct.product.deletedAt.isNull();
	}

}
