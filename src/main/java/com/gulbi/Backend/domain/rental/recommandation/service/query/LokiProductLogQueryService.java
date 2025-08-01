package com.gulbi.Backend.domain.rental.recommandation.service.query;

import com.gulbi.Backend.domain.rental.recommandation.code.WebClientErrorCode;
import com.gulbi.Backend.domain.rental.recommendation.exception.LokiException;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class LokiProductLogQueryService implements ProductLogQueryService {
    private static final String LOKI_GET_REQUEST_ENDPOINT = "/loki/api/v1/query";
    private final WebClient webClient = WebClient.create("http://localhost:3100");
    private final UserService userService;

    public LokiProductLogQueryService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getQueryOfPopularProductIds() {
        String query = LokiQuery.REALTIME_POPULAR_PRODUCT_IDS.getQuery();
        return requestQueryToLoki(query);
    }


    @Override
    public String getQueryOfMostViewedCategoriesByUser() {
        String query = LokiQuery.MOST_VIEWED_THIRD_CATEGORIES_BY_USER.getQuery(getAuthenticationUser().getId());
        return requestQueryToLoki(query);
    }


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