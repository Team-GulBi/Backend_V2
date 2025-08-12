package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.service.image.ImageRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.rental.product.service.product.logging.ProductLogHandler;
import com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search.ProductSearchStrategy;
import com.gulbi.Backend.domain.rental.product.vo.image.Images;
import com.gulbi.Backend.domain.rental.review.dto.ReviewsWithAvg;
import com.gulbi.Backend.domain.rental.review.service.ReviewService;
import com.gulbi.Backend.domain.user.service.ProfileService;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {


    private final ProductRepoService productRepoService;
    private final ImageRepoService imageRepoService;
    private final ReviewService reviewService;
    private final ProfileService profileService;
    //ToDo: 태그검색 제외
    private final Map<String, ProductSearchStrategy> productSearchStrategies;

    private final String className = this.getClass().getName();
    private final ProductLogHandler productLogHandler;

    @Autowired
    public ProductSearchServiceImpl(ProductLogHandler productLogHandler, ProductRepoService productRepoService, ImageRepoService imageRepoService, ReviewService reviewService, ProfileService profileService, Map<String, ProductSearchStrategy> productSearchStrategies) {
        this.productLogHandler = productLogHandler;
        this.productRepoService = productRepoService;
        this.imageRepoService = imageRepoService;
        this.reviewService = reviewService;
        this.profileService = profileService;
        this.productSearchStrategies = productSearchStrategies;
    }

    @Override
    public List<ProductOverViewResponse> searchProductByQuery(ProductSearchRequestDto productSearchRequestDto) {
        String detail = productSearchRequestDto.getDetail().trim();
        String query = productSearchRequestDto.getQuery();
        loggingQuery(query,detail);
        ProductSearchStrategy productSearchStrategy = getProductSearchStrategy(detail, productSearchRequestDto);
        return productSearchStrategy.search(query);
    }

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        //실시간 인기 상품을 위한 로깅
        loggingProductId(productId);

        //상품 조회(User와 FetchJoin)
        Product product = productRepoService.findProductByIdWithUser(productId);

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

    // 예외 호출 (시작)
    private ProductSearchStrategy getProductSearchStrategy(String detail, ProductSearchRequestDto productSearchRequestDto) {
        return Optional.ofNullable(productSearchStrategies.get(detail))
                .orElseThrow(() -> createInvalidProductSearchDetailException(productSearchRequestDto));
    }

    private ProductException.InvalidProductSearchDetailException createInvalidProductSearchDetailException(Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder().args(args).className(className).responseApiCode(ProductErrorCode.UNSUPPORTED_SEARCH_CONDITION).build();
        return new ProductException.InvalidProductSearchDetailException(exceptionMetaData);
    }
    // 예외 호출 (종료)
}
