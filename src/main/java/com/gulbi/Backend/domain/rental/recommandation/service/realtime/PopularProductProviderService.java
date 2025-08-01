package com.gulbi.Backend.domain.rental.recommandation.service.realtime;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.ProductLogQueryService;
import com.gulbi.Backend.domain.rental.recommandation.service.query.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PopularProductProviderService implements PopularProductProvider {
    private final ProductLogQueryService productLogQueryService;
    private final ProductCrudService productCrudService;
    private final QueryHandler queryHandler;

    public PopularProductProviderService(ProductLogQueryService productLogQueryService, ProductCrudService productCrudService, QueryHandler queryHandler) {
        this.productLogQueryService = productLogQueryService;
        this.productCrudService = productCrudService;
        this.queryHandler = queryHandler;
    }
    @Override
    public List<ProductOverViewResponse> getRealTimePopularProducts() {
        String response = productLogQueryService.getQueryOfPopularProductIds();
        List<Long> productIds = queryHandler.getListOfProductIds(response).getProductIds();
        return productCrudService.getProductOverViewByproductIds(productIds);
    }
}
