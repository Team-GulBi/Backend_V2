package com.gulbi.Backend.domain.rental.recommandation.service.personal;

import java.time.LocalDateTime;
import java.util.List;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewPrioritySlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewPrioritySlices;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchCondition;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import com.gulbi.Backend.domain.rental.recommandation.vo.PriorityCategoriesQueue;
import com.gulbi.Backend.global.PersonalCursorRequest;
import com.gulbi.Backend.global.CursorPageable;
import com.gulbi.Backend.global.PersonalCursorPageable;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PersonalRecommendationService implements PersonalProductProvider {
    private final LogQueryService logQueryService;
    private final QueryHandler queryHandler;
	private final ProductRepoService productRepoService;

	public PersonalRecommendationService(LogQueryService logQueryService, QueryHandler queryHandler,
		ProductRepoService productRepoService) {
		this.logQueryService = logQueryService;
		this.queryHandler = queryHandler;
		this.productRepoService = productRepoService;
	}

	@Override
    public ProductOverviewPrioritySlices getPersonalizedRecommendationProducts(PersonalCursorPageable personalCursorPageable) {
		// 클라이언트가 요청한 Pageable 객체
		Pageable requestedPageable = personalCursorPageable.getPageable();
		// 클라이언트가 요청한 페이징 size(pageable)
		int requestSize = requestedPageable.getPageSize();
		// 클라이언트가 요청한 정렬 기준(pageable)
		Sort requestedSort = requestedPageable.getSort();
		// 클라이언트가 요청한 페이징 size에서 상품을 몇개씩 조회해야하는지 비율로 계산
		int[] ratios = splitByRatioWithPriority(requestSize, 6,3,1);

		// Return을 위한 slice객체에 대한 Slices(최종반환)
		ProductOverviewPrioritySlices productOverviewPrioritySlices = new ProductOverviewPrioritySlices();

		//✅[[ 최 초 요 청 시 ]]✅  커서가 없으면 최초 요청임. 유저의 관심사를 추출하고, 가공해야함
		if (personalCursorPageable.getPersonalCursorRequests()==null) {
			// 요청을 보낸 유저의 관심사 카테고리 쌍을 3개를 뽑는 쿼리(추후 더 확장이 가능 함)
			String result = logQueryService.getQueryOfMostViewedCategoriesByUser();
			// 우선 순위 대로 정렬되어있는 우선순위 큐
			PriorityCategoriesQueue priorityCategoriesQueue = queryHandler.getPriorityCategoriesMap(result);
			//시작
			int count = priorityCategoriesQueue.size();
			for (int i=0; i<count; i++) {
				//우선순위 높은 CategoryPair를 순서대로 추출.(N번)
				PriorityCategoriesQueue.CategoryPair categoryPair =  priorityCategoriesQueue.poll();
				// condition객체에 필요한 정보 추출
				Long bCategoryId = categoryPair.getBCategoryId();
				Long mCategoryId = categoryPair.getMCategoryId();
				// count(loki에서 쿼리가 된 횟수) == 우선순위, 추출
				int priority = categoryPair.getCount();
				// 몇개의 상품을 쿼리해야하는지(N번)(위에서 계산한값)
				int queryCount = ratios[i];
				// QueryDSL, Where절에 들어 갈 Condition 객체 생성
				ProductSearchCondition condition = ProductSearchCondition.builder()
					.bCategoryId(bCategoryId)// 카테고리로 필터를 걸기에 카테고리만
					.mCategoryId(mCategoryId)
					.build();
				// Query DSL에 전달 할 Limit, sort가 담긴 Pageable생성
				Pageable pageable = PageRequest.of(0, queryCount, requestedSort);
				// 첫번째 요청이어서 Pageable 만 들어감.
				CursorPageable cursorPageable = new CursorPageable(pageable, null, null);
				// 반환값은 쿼리의 재사용성을 위해 ProductOverviewSlice 타입을 그대로 사용함
				ProductOverviewSlice response = findOverViewByCategoryPair(condition,cursorPageable);
				// 해당 서비스는 priority가 첨가되기때문에 ProductOverviewSlice를 그대로 사용 못함, ProductOverviewPrioritySlice로 변경
				ProductOverviewPrioritySlice productOverviewPrioritySlice = ProductOverviewPrioritySlice.builder()
					.priority(priority)
					.bCategoryId(bCategoryId)
					.mCategoryId(mCategoryId)
					.hasNext(response.isHasNext())
					.nickname(response.getNickname())
					.products(response.getProducts()).build();

				productOverviewPrioritySlices.add(productOverviewPrioritySlice);
			}
			return productOverviewPrioritySlices;
		}

		//✅[[N 번쨰 요청 시]]✅
		List<PersonalCursorRequest> cursors = personalCursorPageable.getPersonalCursorRequests();
		int size = personalCursorPageable.size();
		for (int i = 0; i < size; i++){
			//N번째 요청은 CategoryCursor에 나눠서 담겨 옴
			PersonalCursorRequest personalCursorRequest = cursors.get(i);

			Long bCategoryId = personalCursorRequest.getBCategoryId();
			Long mCategoryId = personalCursorRequest.getMCategoryId();
			// First요청때 념겨준 Priority 다시 회수
			int priority = personalCursorRequest.getPriority();
			//N번째 커서의 LastId, LastCreatedAt 추출
			Long lastReturnProductId = personalCursorRequest.getLastProductId();
			LocalDateTime lastReturnCreatedAt = personalCursorRequest.getLastCreatedAt();
			// 계산된 비율에 따라 몇개를 쿼리 해야 할지 추출
			int queryCount = ratios[i];
			// Query DSL에 전달 할 Limit, sort가 담긴 Pageable생성
			// 요청으로 온 1개의 Pageble을 N개로 쪼개서 쿼리해야함
			Pageable pageable = PageRequest.of(0, queryCount, requestedSort);
			// QueryDSL의 Where절 필터링을 위한 ProductSearchCondition
			ProductSearchCondition condition = ProductSearchCondition.builder()
				.mCategoryId(mCategoryId) // 조건은 카테고리이므로 카테고리만 빌더로 생성
				.bCategoryId(bCategoryId)
				.build();
			// QueryDSL에 커서 조건을 위한 CursorPageable객체 생성
			CursorPageable cursorPageable = new CursorPageable(pageable, lastReturnProductId, lastReturnCreatedAt);
			// 반환값은 쿼리의 재사용성을 위해 ProductOverviewSlice
			ProductOverviewSlice productOverviewSlice = findOverViewByCategoryPair(condition, cursorPageable);
			// 해당 서비스는 priority가 첨가되기때문에 ProductOverviewPrioritySlice로 변경
			ProductOverviewPrioritySlice productOverviewPrioritySlice = ProductOverviewPrioritySlice.builder()
				.priority(priority)
				.bCategoryId(bCategoryId)
				.mCategoryId(mCategoryId)
				.hasNext(productOverviewSlice.isHasNext())
				.nickname(productOverviewSlice.getNickname())
				.products(productOverviewSlice.getProducts()).build();

			productOverviewPrioritySlices.add(productOverviewPrioritySlice);

		}
		return productOverviewPrioritySlices;
	}

	private int[] splitByRatioWithPriority(int size, int... ratios) {
		if (size < 0) throw new IllegalArgumentException("size must be >= 0");
		if (ratios == null || ratios.length == 0) throw new IllegalArgumentException("ratios required");
		int n = ratios.length;
		long total = 0;
		for (int r : ratios) {
			if (r < 0) throw new IllegalArgumentException("ratio must be >= 0");
			total += r;
		}
		if (total == 0) throw new IllegalArgumentException("sum(ratios) must be > 0");

		int[] result = new int[n];

		// 1) 기본 몫 (내림)
		long used = 0;
		for (int i = 0; i < n; i++) {
			long share = (long) size * ratios[i] / total; // 정수 나눗셈
			result[i] = (int) share;
			used += share;
		}

		// 2) 남는 몫
		int remainder = (int) (size - used);
		if (remainder == 0) return result;

		// 3) 우선순위: ratio 큰 순, 동률이면 인덱스 작은 순
		Integer[] order = new Integer[n];
		for (int i = 0; i < n; i++) order[i] = i;
		java.util.Arrays.sort(order, (i, j) -> {
			int cmp = Integer.compare(ratios[j], ratios[i]); // 큰 비율 우선
			if (cmp == 0) cmp = Integer.compare(i, j);       // 동률이면 앞쪽 인덱스
			return cmp;
		});

		// 4) 남은 만큼 1씩 배분 (우선순위 높은 인덱스부터, 필요하면 순환)
		int idx = 0;
		while (remainder-- > 0) {
			result[order[idx]]++;
			idx = (idx + 1) % n;
		}
		return result;
	}

	private ProductOverviewSlice findOverViewByCategoryPair(ProductSearchCondition categoryPair, CursorPageable pageable){
		try {
			return productRepoService.findOverViewByCategoryPair(categoryPair,pageable);
		}catch (ProductException e){
			return ProductOverviewSlice.builder().hasNext(false).nickname(null).products(null).build();

		}

	}

}
