package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.logging.ProductLogHandler;
import com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search.ProductSearchStrategy;
import com.gulbi.Backend.domain.rental.product.vo.Images;
import com.gulbi.Backend.domain.rental.review.dto.ReviewsWithAvg;
import com.gulbi.Backend.domain.rental.review.service.ReviewService;
import com.gulbi.Backend.global.CursorPageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {


    private final ProductRepoService productRepoService;
    private final ImageRepoService imageRepoService;
    private final ReviewService reviewService;
    //ToDo: 태그검색 제외
    private final Map<String, ProductSearchStrategy> productSearchStrategies;

    private final String className = this.getClass().getName();
    private final ProductLogHandler productLogHandler;

    @Autowired
    public ProductSearchServiceImpl(ProductLogHandler productLogHandler, ProductRepoService productRepoService, ImageRepoService imageRepoService, ReviewService reviewService, Map<String, ProductSearchStrategy> productSearchStrategies) {
        this.productLogHandler = productLogHandler;
        this.productRepoService = productRepoService;
        this.imageRepoService = imageRepoService;
        this.reviewService = reviewService;
        this.productSearchStrategies = productSearchStrategies;
    }

    @Override
    public ProductOverviewSlice searchProductByQuery(ProductSearchRequest productSearchRequest, CursorPageable cursorPageable) {
        String detail = productSearchRequest.getDetail().trim();
        String query = productSearchRequest.getQuery();
        loggingQuery(query,detail);
        //ToDo: 태그 보류, 추가 된다면 예외처리 부터
        ProductSearchStrategy productSearchStrategy = productSearchStrategies.get(detail);
        return productSearchStrategy.search(query,cursorPageable);
    }

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        //실시간 인기 상품을 위한 로깅
        loggingProductId(productId);

        //상품 조회(User와 FetchJoin)
        Product product = productRepoService.findByIdWithUser(productId);

        //상품 이미지 조회
        Images productImages = imageRepoService.findImagesByProductId(productId);
        //상품 리뷰 조회(리뷰가 없을 수 있음)
        ReviewsWithAvg reviewsWithAvg = new ReviewsWithAvg(reviewService.getAllReview(productId));

        //맞춤상품을 위한 ? 로깅
        loggingReturnedProduct(product);

        return ProductDetailResponse.of(product, productImages, reviewsWithAvg);
    }



    // 로깅 서비스 호출 메서드 부분(시작)
    private void loggingProductId(Long productId){
        productLogHandler.loggingProductIdData(productId);
    }
    private void loggingQuery(String query, String detail){
        productLogHandler.loggingQueryData(query,detail);
    }
    private void loggingReturnedProduct(Product product){
        productLogHandler.loggingReturnedProductData(product);
    }
    // 로깅 서비스 호출 메서드 부분(끝)



}
