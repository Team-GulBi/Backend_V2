package com.gulbi.Backend.global.test;

import com.gulbi.Backend.domain.rental.product.entity.Category;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.repository.CategoryRepository;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.repository.ReviewRepository;
import com.gulbi.Backend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ProductDummyService {

    private final CategoryRepository categoryRepository;
    private final ReviewRepository reviewRepository;
    // 리뷰 데이터를 저장할 static 필드
    private static final List<Review> reviews = new ArrayList<>();

    // 지역을 랜덤으로 선택하여 반환하는 메서드
    public String[] getRandomRegion(){
        final Map<String,List<String>> sigunguMap = new HashMap<>();
        sigunguMap.put("서울",Arrays.asList("강남구","송파구","성북구","강서구","금천구","노원구","도봉구","동작구","마포구","강동구","양천구"));
        sigunguMap.put("인천",Arrays.asList("부평구","계양구","서구","중구","미추홀구","연수구","강화군","옹진군"));
        sigunguMap.put("부산", Arrays.asList("해운대구", "사하구", "남구", "동래구", "금정구", "북구", "사상구", "수영구", "연제구", "기장군"));
        sigunguMap.put("대구", Arrays.asList("중구", "동구", "서구", "남구", "북구", "수성구", "달서구", "달성군"));
        sigunguMap.put("대전", Arrays.asList("동구", "중구", "서구", "유성구", "대덕구"));
        sigunguMap.put("광주", Arrays.asList("동구", "서구", "남구", "북구", "광산구"));
        final String[] sidoList ={"서울","인천","부산","대구","대전","광주"};

        int randomIndex = (int)(Math.random()*sidoList.length);
        String sido = sidoList[randomIndex];

        List<String> sigunguList = sigunguMap.get(sido);
        randomIndex = (int)(Math.random()*sigunguList.size());
        String sigungu = sigunguList.get(randomIndex);

        return new String[]{sido, sigungu};
    }

    public String getRandomProductName() {
        final String[] productName = {"삼성", "에플", "LG"};
        final String[] productName2 = {"노트북", "휴대폰", "냉장고", "세탁기", "TV", "이어폰", "스피커", "PC"};
        String randomProductName = productName[(int)(Math.random()*productName.length)];
        String randomProductName2 = productName2[(int)(Math.random()*productName2.length)];
        return randomProductName + randomProductName2;
    }

    public String getRandomTag() {
        final String[] tagList = {
                "노트북", "m1칩", "전남친", "군대", "여행", "취업", "헬스", "다이어트", "아이폰", "맥북",
                "유튜브", "SNS", "쇼핑", "재테크", "보험", "부동산", "투자", "자격증", "교육", "캠핑",
                "드라마", "영화", "음악", "플레이리스트", "독서", "요리", "반려동물", "자동차", "자전거", "등산",
                "취미", "요가", "피트니스", "사진", "여행지", "커피", "디지털", "뷰티", "패션", "일상",
                "맛집", "라이프", "브이로그", "아이돌", "연예인", "비즈니스", "회사생활", "대학생", "영어공부", "중국어",
                "일본어", "자기계발", "아침습관", "프로젝트", "기술", "개발자", "코딩", "프로그래밍", "컴퓨터", "가전제품",
                "디자인", "캘리그라피", "프리랜서", "가족", "친구", "결혼", "연애", "인테리어", "집꾸미기", "이사",
                "부모님", "육아", "어린이", "운동", "다이어리", "생활용품", "음료", "청소", "플라워", "식물",
                "심리학", "스마트폰", "자연", "환경", "비건", "사회복지", "동물권", "중고거래", "서점", "창업"
        };
        int count = (int)(Math.random()*7);
        Set<String> tags = new HashSet<>();

        for (int i = 0; i < count; i++) {
            int randomIndex = (int)(Math.random()*tagList.length);
            tags.add(tagList[randomIndex]);
        }

        return String.join(",", tags);
    }

    public Integer getRandomPrice() {
        return (int)(Math.random()*1000000);
    }

    public List<Category> getRandomCategory() {
        List<Category> bcategoryList = categoryRepository.findAllNoParent();
        int randomIndex = (int)(Math.random()*bcategoryList.size());
        Category bcategory = bcategoryList.get(randomIndex);

        List<Category> mcategoryList = categoryRepository.findByParent(bcategory);
        randomIndex = (int)(Math.random()*mcategoryList.size());
        Category mcategory = mcategoryList.get(randomIndex);

        List<Category> scategoryList = categoryRepository.findByParent(mcategory);
        randomIndex = (int)(Math.random()*scategoryList.size());
        Category scategory = scategoryList.get(randomIndex);

        return Arrays.asList(bcategory, mcategory, scategory);
    }

    public void setRandomReview(Product product, User user) {
        String[] contentsList = {
                "판매자분이 너무 친절해요",
                "물건 상태가 많이 안좋네요 감안하고 사용하시는거 추천합니다.",
                "굿",
                "물건에 살짝 스크레치가 났는데 전부 변상하라고 하네요.. 참고하세요",
                "너무 잘 썼습니다 감사합니다.",
                "대여 기간이 적당하고, 사용하기 편했어요!",
                "장비 상태가 아주 좋습니다. 대여해서 잘 썼어요.",
                "사이즈가 좀 작았지만, 나름 괜찮았습니다.",
                "대여 후 사용 방법이 간단해서 좋았어요.",
                "반납 과정이 매끄러워서 좋았습니다.",
                "서비스가 빠르고 간편해서 재대여할 예정이에요.",
                "대여가 가능한 기간이 길어서 여러 번 사용했어요.",
                "친절한 설명 덕분에 처음 사용했지만 문제없이 썼습니다.",
                "대여 비용이 적당해서 부담이 없었습니다.",
                "장비를 사용해 보니 기대 이상이에요!"
        };
        int count = (int)(Math.random()*2); // 한 상품당 리뷰가 1 ~ N개 생성

        for (int i = 0; i < count; i++) {
            int randomIndex = (int)(Math.random()*contentsList.length);
            String content = contentsList[randomIndex];
            int rating = (int)(Math.random()*5) + 1;

            Review review = Review.builder()
                    .content(content)
                    .rating(rating)
                    .product(product)
                    .user(user)
                    .build();

            reviews.add(review);
        }
    }

    // reviews 리스트를 반환하는 메서드
    public static List<Review> getReviews() {
        return new ArrayList<>(reviews);
    }
}
