package com.gulbi.Backend.domain.rental.recommandation.vo;
import com.gulbi.Backend.domain.rental.recommandation.code.RecommendationErrorCode;
import com.gulbi.Backend.global.error.BusinessException;
import com.gulbi.Backend.global.error.ExceptionMetaData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PopularProductIds {
    final private List<Long> productIds;

    private PopularProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }

    private PopularProductIds(List<String> productIds, boolean fromString) {
        this.productIds = parseStringListToLongList(productIds);
    }

    public static PopularProductIds fromStrings(List<String> productIds){
        return new PopularProductIds(productIds, true);
    }

    public static PopularProductIds fromLongs(List<Long> productIds){
        return new PopularProductIds(productIds);
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
