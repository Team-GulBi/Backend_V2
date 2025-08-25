// package com.gulbi.Backend.domain.rental.recommandation.vo;
//
// import java.util.Map;
// import java.util.UUID;
//
// import com.gulbi.Backend.domain.rental.recommandation.dto.CategoryPair;
//
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;
//
// public class PriorityCategoriesMap {
// 	private Map<String, CategoryPair> map;
// 	@AllArgsConstructor
// 	@Getter
// 	public static class CategoryPair{
// 		private final Long bigCategoryId;
// 		private final Long midCategoryId;
// 	}
//
// 	public PriorityCategoriesMap() {
// 	}
//
// 	public Map<String, CategoryPair> getMap() {
// 		return map;
// 	}
//
// 	public void put(String priority, CategoryPair pair){
// 		map.put(addUUID(priority), pair);
// 	}
//
// 	private String addUUID(String key){
// 		UUID uuid = UUID.randomUUID();
// 		String strUuid = uuid.toString();
// 		String parsedPriority = key + "-" + strUuid;
// 		return parsedPriority;
// 	}
//
// 	private String subUUID(String key){
// 		return key.contains("-") ? key.substring(0,key.indexOf("-")) : key;
//
// 	}
//
// }
