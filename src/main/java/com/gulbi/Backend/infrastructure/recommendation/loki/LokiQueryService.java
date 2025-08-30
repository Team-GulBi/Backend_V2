package com.gulbi.Backend.infrastructure.recommendation.loki;

import com.gulbi.Backend.domain.rental.recommandation.exception.LokiException;
import com.gulbi.Backend.domain.rental.recommandation.service.query.LogQueryService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.Value;

@Service
public class LokiQueryService implements LogQueryService {

    private final String baseUrl;
    private final String queryEndpoint;
    private final WebClient webClient;
    private final UserService userService;

    public LokiQueryService(
        @Value("${loki.base-url}") String baseUrl,
        @Value("${loki.query-endpoint}") String queryEndpoint,
        UserService userService
    ) {
        this.baseUrl = baseUrl;
        this.queryEndpoint = queryEndpoint;
        this.userService = userService;
        this.webClient = WebClient.create(baseUrl);
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

    private String requestQueryToLoki(String query) {
        return webClient.get()
            .uri(uriBuilder -> uriBuilder.path(queryEndpoint)
                .queryParam("query", query)
                .build())
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response -> {
                ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder()
                    .stackTrace(new Throwable())
                    .className(this.getClass().getName())
                    .responseApiCode(WebClientErrorCode.WEB_CLIENT_BAD_REQUEST)
                    .build();
                throw new LokiException.ResponseException(exceptionMetaData);
            })
            .onStatus(HttpStatusCode::is5xxServerError, response -> {
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

    private User getAuthenticationUser() {
        return userService.getAuthenticatedUser();
    }
}
