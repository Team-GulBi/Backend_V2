// package com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy.latest;
//
// import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
// import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
// import com.gulbi.Backend.domain.rental.recommandation.service.personal.strategy.CategoryBasedRecommendStrategy;
// import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
// import com.gulbi.Backend.domain.rental.recommandation.vo.PersonalCategoryPagination;
// import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationRequestDto;
// import com.gulbi.Backend.domain.rental.recommandation.dto.PersonalRecommendationResponseDto;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
//
// @Service("latestProductQueryStrategy")
// //전략에 대한 정보만 유치 필요한 상품의 비율은 서비스 호출로 충족.
// public class LatestRecommendationStrategy implements CategoryBasedRecommendStrategy {
//     private final ProductRepoService productRepoService;
//     //비율은 우선순위대로 50%, 30%, 10%,5%,5% 하겠습니다.
//     private static final int RECOMMENDATION_RATE_100 = 10;
//     private static final int RECOMMENDATION_RATE_70 = 7;
//     private static final int RECOMMENDATION_RATE_60 = 6;
//     private static final int RECOMMENDATION_RATE_30 = 3;
//     private static final int RECOMMENDATION_RATE_10 = 1;
//
//     public LatestRecommendationStrategy(ProductRepoService productRepoService) {
//         this.productRepoService = productRepoService;
//     }
//
//     @Override
//     public PersonalRecommendationResponseDto getRecommendatedProducts(ExtractedRecommendation extractedRecommendation) {
//
//         List<Integer> sortedKeys = extractedRecommendation.getSortedKey();
//         List<Integer> rateList = getRateListBySize(sortedKeys.size());
//         List<List<ProductOverViewResponse>> recommendedProducts = new ArrayList<>();
//
//         for (int i=0; i<sortedKeys.size(); i++) {
//             Pageable pageable = PageRequest.of(0,rateList.get(i));
//             int priority = sortedKeys.get(i);
//             Long bCategoryId = Long.valueOf(extractedRecommendation.getBCategory(priority));
//             Long mCategoryId = Long.valueOf(extractedRecommendation.getMCategory(priority));
//             recommendedProducts.add(
//                     productRepoService.findOverViewByCategories(bCategoryId, mCategoryId, null, null, pageable)
//             );
//         }
//         return new PersonalRecommendationResponseDto(recommendedProducts);
//     }
//
//     @Override
//     public PersonalRecommendationResponseDto getRecommendatedProducts(PersonalRecommendationRequestDto personalRecommendationRequestDto) {
//         PersonalCategoryPagination personalCategoryPagination = PersonalCategoryPagination.of(personalRecommendationRequestDto.getCategories());
//         List<Integer> sortedKeys= personalCategoryPagination.getSortedCategoryIdsDesc();
//         List<Integer> rateList = getRateListBySize(sortedKeys.size());
//         List<List<ProductOverViewResponse>> recommendedProducts = new ArrayList<>();
//         for (int i=0; i<sortedKeys.size(); i++) {
//             Pageable pageable = PageRequest.of(0,rateList.get(i));
//             int priority = sortedKeys.get(i);
//             Long bCategoryId = Long.valueOf(personalCategoryPagination.getBCategoryIdByPriority(priority));
//             Long mCategoryId = Long.valueOf(personalCategoryPagination.getMCategoryIdByPriority(priority));
//             LocalDateTime lastCreatedAt = personalCategoryPagination.getLastCreatedByPriority(priority);
//             recommendedProducts.add(
//                     productRepoService.findOverViewByCategories(bCategoryId, mCategoryId, null, lastCreatedAt, pageable)
//             );
//         }
//         return new PersonalRecommendationResponseDto(recommendedProducts);
//     }
//
//     private List<Integer> getRateListBySize(int size){
//         if (size == 3){
//             return List.of(RECOMMENDATION_RATE_60,RECOMMENDATION_RATE_30,RECOMMENDATION_RATE_10);
//         }
//         if(size == 2){
//             return List.of(RECOMMENDATION_RATE_70,RECOMMENDATION_RATE_30);
//         }
//         if(size == 1){
//             return List.of(RECOMMENDATION_RATE_100);
//         }
//         return null;
//         //ToDo : 예외처리
//     }
// }
// //        map<integer,map<string, integer>>
// //        {
// //            1: { "bCategory": 10, "mCategory": 300 },
// //            12: { "bCategory": 10, "mCategory": 300 },
// //            9: { "bCategory": 10, "mCategory": 300 },
// //        }