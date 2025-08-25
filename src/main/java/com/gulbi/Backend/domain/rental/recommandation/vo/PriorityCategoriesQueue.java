package com.gulbi.Backend.domain.rental.recommandation.vo;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.UUID;

import lombok.Getter;

public class PriorityCategoriesQueue {
	@Getter
	public static class CategoryPair{

		private final Long bCategoryId;
		private final Long mCategoryId;
		private final Long count;
		private final String uuid;

		public CategoryPair(Long bCategoryId, Long mCategoryId, Long count) {
			this.bCategoryId = bCategoryId;
			this.mCategoryId = mCategoryId;
			this.count = count;
			this.uuid = UUID.randomUUID().toString();
		}
		public CategoryPair(String bCategoryId, String mCategoryId, String count) {
			this.bCategoryId = Long.parseLong(bCategoryId);
			this.mCategoryId = Long.parseLong(mCategoryId);
			this.count = Long.parseLong(count);
			this.uuid = UUID.randomUUID().toString();
		}

	}

	private final PriorityQueue<CategoryPair> categoryPairPriorityQueue = new PriorityQueue<CategoryPair>(
		new Comparator<CategoryPair>() {
			@Override
			public int compare(CategoryPair o1, CategoryPair o2) {
				int compared = Long.compare(o2.count, o1.count); // 큰 순서내로 내림차순 할거라 반대로 뒤집었음.
				if(compared == 0) // 중복일때 UUID큰 순서대로
					compared = o1.getUuid().compareTo(o2.getUuid());
				return compared;
			}
		});

	public void add(CategoryPair categoryPair){
		categoryPairPriorityQueue.add(categoryPair);
	}

	public void poll(){
		categoryPairPriorityQueue.poll();
	}

	public int size(){
		return categoryPairPriorityQueue.size();
	}


}
