package com.gulbi.Backend.domain.rental.recommandation.service.realtime;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;

import java.util.List;

public interface PopularProductProvider {
    List<ProductOverViewResponse> getRealTimePopularProducts();
}
