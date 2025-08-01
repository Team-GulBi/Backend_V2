package com.gulbi.Backend.domain.rental.recommandation.vo;
import com.gulbi.Backend.domain.rental.recommandation.code.RecommendationErrorCode;
import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExtractedProductIds {
    final private List<Long> productIds;

    private ExtractedProductIds(List<String> productIds) {
        this.productIds = parseStringListToLongList(productIds);
    }

    public static ExtractedProductIds of(List<String> productIds){
        return new ExtractedProductIds(productIds);
    }

    public List<Long> getProductIds(){
        if (!productIds.isEmpty()) {
            return new ArrayList<>(productIds);
        }
        listIsNullException();
        return null;
    }
    private List<Long> parseStringListToLongList(List<String> productIds) {
        if (!productIds.isEmpty()) {
            List<Long> longParsedList = new ArrayList<>();
            for (String data : productIds) {
                if(Objects.equals(data, "")){
                    continue;
                }
                longParsedList.add(parseStringToLong(data));
            }
            return longParsedList;
        }
        listIsNullException();
        return null;
    }

    private Long parseStringToLong(String data){
        return Long.valueOf(data);
    }

    private void listIsNullException(){
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder().responseApiCode(RecommendationErrorCode.REALTIME_POPULAR_PRODUCT_DOES_NOT_EXIST).build();
        throw new BusinessException(exceptionMetaData);
    }

}
