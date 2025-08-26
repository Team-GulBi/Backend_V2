// package com.gulbi.Backend.domain.rental.recommandation.vo;
//
// import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
//
// import java.time.LocalDateTime;
// import java.util.Comparator;
// import java.util.List;
// import java.util.Map;
//
// public class PersonalCategoryPagination {
//
//     private final Map<Integer, Map<String, PersonalRecommendationRequestDto.Value>> categories;
//
//     private PersonalCategoryPagination(Map<Integer, Map<String, PersonalRecommendationRequestDto.Value>> categories) {
//         this.categories = categories;
//     }
//     public static PersonalCategoryPagination of(Map<Integer, Map<String, PersonalRecommendationRequestDto.Value>> categories) {
//         return new PersonalCategoryPagination(categories);
//     }
//
//     public List<Integer> getSortedCategoryIdsDesc() {
//         return this.categories.keySet().stream().sorted(Comparator.reverseOrder()).toList();
//     }
//
//     public Integer getBCategoryIdByPriority(Integer priority){
//         return this.categories.get(priority).get("bCategoryId").getIntValue();
//     }
//     public Integer getMCategoryIdByPriority(Integer priority){
//         return this.categories.get(priority).get("mCategoryId").getIntValue();
//     }
//     public LocalDateTime getLastCreatedByPriority(Integer priority){
//         return this.categories.get(priority).get("lastCreatedAt").getDateTimeValue();
//     }
//
// }