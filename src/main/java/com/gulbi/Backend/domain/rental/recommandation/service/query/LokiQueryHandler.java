package com.gulbi.Backend.domain.rental.recommandation.service.query;

import com.gulbi.Backend.domain.rental.recommandation.code.JsonPathErrorCode;
import com.gulbi.Backend.domain.rental.recommandation.exception.JsonPathException;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedProductIds;
import com.gulbi.Backend.domain.rental.recommandation.vo.ExtractedRecommendation;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LokiQueryHandler implements QueryHandler{
    private final String className = this.getClass().getName();
    private static final String EXTRACT_BIG_CATEGORY_IDS ="$.data.result[*].metric.bCategoryId";
    private static final String EXTRACT_MIDDLE_CATEGORY_IDS ="$.data.result[*].metric.mCategoryId";
    private static final String EXTRACT_PRIORITY ="$.data.result[*].value[1]";
    @Override
    public ExtractedProductIds getListOfProductIds(String queryResult) {
        return ExtractedProductIds.of(JsonPath.read(queryResult, "$.data.result[*].metric.productId"));
    }

    @Override
    public ExtractedRecommendation getMapOfRecommandation(String queryResult) {
        // 실패작들
        //        System.out.println(Optional.ofNullable(JsonPath.read(queryResult, "$.data.result[*].value[*].values")));
        //        System.out.println(Optional.ofNullable(JsonPath.read(queryResult, "$.data.result[*].metric[*].bCategoryId")));
//        System.out.println(queryResult);
//        System.out.println(Optional.ofNullable(JsonPath.read(queryResult, "$.data.result[*].metric.bCategoryId")));
//        System.out.println(Optional.ofNullable(JsonPath.read(queryResult, "$.data.result[*].metric.mCategoryId")));
//        System.out.println(Optional.ofNullable(JsonPath.read(queryResult, "$.data.result[*].value[1]")));
        try {
            List<String> bCategoryList = JsonPath.read(queryResult, EXTRACT_BIG_CATEGORY_IDS);
            List<String> mCategoryList = JsonPath.read(queryResult, EXTRACT_MIDDLE_CATEGORY_IDS);
            List<String> priorityList = JsonPath.read(queryResult, EXTRACT_PRIORITY);
            return ExtractedRecommendation.of(bCategoryList, mCategoryList, priorityList);
        }catch(Exception e){
            ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                    .responseApiCode(JsonPathErrorCode.JSONPATH_CANT_PARSE_QUERY)
                    .className(className)
                    .args(queryResult)
                    .stackTrace(e).build();
            throw new JsonPathException.jsonPathCantParseQueryException(exceptionMetaData);
        }

    }

}
