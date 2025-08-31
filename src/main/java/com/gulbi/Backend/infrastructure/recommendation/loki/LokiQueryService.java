package com.gulbi.Backend.infrastructure.recommendation.loki;

import com.gulbi.Backend.domain.rental.recommandation.exception.LokiException;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LokiQueryService implements LogQueryService {
    private static final String LOKI_GET_REQUEST_ENDPOINT = "/loki/api/v1/query";
    private final WebClient webClient = WebClient.create("http://loki:3100");
    private final UserService userService;

    public LokiQueryService(UserService userService) {
        this.userService = userService;
    }
    //인기상품 조회, 반환결과 Json
    @Override
    public String getQueryOfPopularProductIds() {
        //인기상품 쿼리문 추출
        // 최근 600분동안 로그에 집계된 많이 조회된 20개의 상품 아이디 반환
        String query = LokiQuery.REALTIME_POPULAR_PRODUCT_IDS.getQuery();
        return requestQueryToLoki(query);
    }


    @Override
    public String getQueryOfMostViewedCategoriesByUser() {
        String query = LokiQuery.MOST_VIEWED_THIRD_CATEGORIES_BY_USER.getQuery(getAuthenticationUser().getId());
        return requestQueryToLoki(query);
    }


    //실제로 쿼리를 날리는 부분
    private String requestQueryToLoki(String query){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(LOKI_GET_REQUEST_ENDPOINT)
                        .queryParam("query", "{query}")
                        .build(query))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,response->{
                    ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                            .stackTrace(new Throwable())
                            .className(this.getClass().getName())
                            .responseApiCode(WebClientErrorCode.WEB_CLIENT_BAD_REQUEST)
                            .build();
                    throw new LokiException.ResponseException(exceptionMetaData);
                })
                .onStatus(HttpStatusCode::is5xxServerError,response->{
                    ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                            .stackTrace(new Throwable())
                            .className(this.getClass().getName())
                            .responseApiCode(WebClientErrorCode.INTERNAL_SERVER_ERROR)
                            .build();
                    throw new LokiException.ResponseException(exceptionMetaData);
                })
                .bodyToMono(String.class)
                .block();
    }

    private User getAuthenticationUser(){
        return userService.getAuthenticatedUser();
    }
}
