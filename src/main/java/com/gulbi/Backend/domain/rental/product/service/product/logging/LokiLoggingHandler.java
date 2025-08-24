package com.gulbi.Backend.domain.rental.product.service.product.logging;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;

import org.slf4j.*;
import org.springframework.stereotype.Service;

@Service
public class LokiLoggingHandler implements ProductLogHandler{
    private static final Logger loggingProductIdData = LoggerFactory.getLogger("com.gulbi.Backend.domain.rental.product.service.product.logging.LokiLoggingHandler.loggingProductIdData");
    private static final Logger loggingReturnedProductData = LoggerFactory.getLogger("com.gulbi.Backend.domain.rental.product.service.product.logging.LokiLoggingHandler.loggingReturnedProductData");
    private final UserService userService;

    public LokiLoggingHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void loggingQueryData(String query, String detail) {
       MDC.put("query", query);
       MDC.put("detail", detail);
       MDC.clear();
    }

    @Override
    public void loggingProductIdData(Long productId) {
        MDC.put("productId", String.valueOf(productId));
        loggingProductIdData.info("Queried ProductId Information For RealTime Products Recommandation ");
        MDC.clear();
    }

    @Override
    public void loggingReturnedProductData(Product product) {
        User user = userService.getAuthenticatedUser();
        MDC.put("userId", String.valueOf(user.getId()));
        MDC.put("bCategoryId", String.valueOf(product.getBCategory().getId()));
        MDC.put("mCategoryId", String.valueOf(product.getMCategory().getId()));
        MDC.put("sCategoryId", String.valueOf(product.getSCategory().getId()));
        loggingReturnedProductData.info("Categories Information Queried by the User for Product Recommendation");
        MDC.clear();
    }

    private User getAuthenticationUser(){
        return userService.getAuthenticatedUser();
    }
}
