package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchCondition;
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
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.CursorPageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductSearchServiceImpl implements ProductSearchService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ProductRepoService productRepoService;
    private final ImageRepoService imageRepoService;
    private final ReviewService reviewService;
    //ToDo: 태그검색 제외
    private final Map<String, ProductSearchStrategy> productSearchStrategies;

    private final String className = this.getClass().getName();
    private final ProductLogHandler productLogHandler;


    // 단순 조회가 아닌 검색기반에만 전략 사용
    @Override
    public ProductOverviewSlice searchProductByQuery(ProductSearchRequest productSearchRequest, CursorPageable cursorPageable) {
        String detail = productSearchRequest.getDetail().trim();
        String query = productSearchRequest.getQuery();
        loggingQuery(query,detail);
        ProductSearchStrategy productSearchStrategy = productSearchStrategies.get(detail);
        return productSearchStrategy.search(query,cursorPageable);
    }

    @Override
    public ProductDetailResponse getProductDetail(Long productId) {
        //실시간 인기 상품을 위한 로깅
        loggingProductId(productId);

        //상품 조회(User와 FetchJoin)
        Product product = productRepoService.findByIdWithUser(productId);

        //상품 소유자 조회
        User productOwner = product.getUser();

        //요청자 신원 조회
        User requestUser = userService.getAuthenticatedUser();

        //상품 소유자 인지 판단
        boolean owner = Objects.equals(productOwner.getId(), requestUser.getId());

        //상품 이미지 조회
        Images productImages = imageRepoService.findImagesByProductId(productId);
        //상품 리뷰 조회(리뷰가 없을 수 있음)
        ReviewsWithAvg reviewsWithAvg = new ReviewsWithAvg(reviewService.getAllReview(productId));

        //맞춤상품을 위한 ? 로깅
        loggingReturnedProduct(product);

        return ProductDetailResponse.of(product, productImages, reviewsWithAvg,owner);
    }

    @Override
    public ProductOverviewSlice getMyProducts(CursorPageable cursorPageable) {
        User user = userService.getAuthenticatedUser();
        ProductSearchCondition condition = ProductSearchCondition.builder().user(user).build();
        ProductOverviewSlice productOverviewSlice = productRepoService.findOverViewByUser(condition, cursorPageable);
        return new ProductOverviewSlice(user.getNickname(),productOverviewSlice.isHasNext(),productOverviewSlice.getProducts());
    }

    @Override
    public ProductOverviewSlice getUserProducts(Long userId,CursorPageable cursorPageable) {
        User user = userRepository.findById(userId).orElseThrow();
        ProductSearchCondition condition = ProductSearchCondition.builder().user(user).build();
        ProductOverviewSlice productOverviewSlice = productRepoService.findOverViewByUser(condition, cursorPageable);
        return new ProductOverviewSlice(user.getNickname(),productOverviewSlice.isHasNext(),productOverviewSlice.getProducts());
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
