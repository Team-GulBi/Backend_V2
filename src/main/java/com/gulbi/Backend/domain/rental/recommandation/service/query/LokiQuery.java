package com.gulbi.Backend.domain.rental.recommandation.service.query;

public enum LokiQuery {

    REALTIME_POPULAR_PRODUCT_IDS("topk(20, sum(count_over_time({job=\"popularProduct\"} | json | line_format \"{{.productId}}\" [600m])) by (productId))"),

    MOST_VIEWED_THIRD_CATEGORIES_BY_USER("topk(3, sum(count_over_time({job=\"personalRecommandationProduct\"} \n" +
                                                 "    | json \n" +
                                                 "    | metadata_userId == %d\n" + //유저 아이디 동적 할당
                                                 "    | line_format \"{{.bCategoryId}},{{.mCategoryId}}\" [1000m])) \n" +
                                                 "    by (bCategoryId, mCategoryId))");
    private final String query;

    LokiQuery(String query) {
        this.query = query;
    }

    //ToDo: 타입 안전성도 그렇고 쿼리가 더 추가된다면 동적 쿼리에 대해서는 개별 매서드를 만들지, 유지할지 고민을 해봐야함
    public String getQuery(Object... param){
        return String.format(this.query, param);
    }
}
