package com.gulbi.Backend.domain.rental.product.service.product.search;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageDtoCollection;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductDto;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.response.ProductDetailResponseDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.domain.rental.product.service.image.ImageCrudService;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.product.service.product.logging.ProductLogHandler;
import com.gulbi.Backend.domain.rental.product.service.product.search.strategy.search.ProductSearchStrategy;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.review.dto.ReviewWithAvgProjection;
import com.gulbi.Backend.domain.rental.review.service.ReviewService;
import com.gulbi.Backend.domain.user.dto.ProfileResponseDto;
import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.ProfileService;
import com.gulbi.Backend.domain.user.service.UserService;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
public class ProductSearchServiceImpl implements ProductSearchService {


    private final ProductCrudService productCrudService;
    private final ImageCrudService imageCrudService;
    private final ReviewService reviewService;
    private final ProfileService profileService;

    private final Map<String, ProductSearchStrategy> productSearchStrategies;

    private final String className = this.getClass().getName();
    private final ProductLogHandler productLogHandler;

    @Autowired
    public ProductSearchServiceImpl(ProductLogHandler productLogHandler, ProductCrudService productCrudService, ImageCrudService imageCrudService, ReviewService reviewService, ProfileService profileService, Map<String, ProductSearchStrategy> productSearchStrategies) {
        this.productLogHandler = productLogHandler;
        this.productCrudService = productCrudService;
        this.imageCrudService = imageCrudService;
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
    public ProductDetailResponseDto getProductDetail(Long productId) {
        loggingProductId(productId);
        ProductDto product = getProductById(productId);
        loggingReturnedProduct(product);
        ProductImageDtoCollection imageList = getProductImagesByProductId(productId);
        List<ReviewWithAvgProjection> reviewWithAvg = getProductReviewsByProductId(productId);
        ImageUrl userPhoto = getImageOfUser(product);
        String userNickname = getUserName(product);
        return ProductDetailResponseDto.of(product, imageList, reviewWithAvg,userPhoto,userNickname);
    }

    private ProductDto getProductById(Long productId) {
        return productCrudService.getProductDtoById(productId);
    }

    private ProductImageDtoCollection getProductImagesByProductId(Long productId) {
        return imageCrudService.getImageByProductId(productId);
    }

    private List<ReviewWithAvgProjection> getProductReviewsByProductId(Long productId) {
        return reviewService.getAllReview(productId);
    }

    private ProfileResponseDto getProfile(Long userId){
        return profileService.getProfile(userId);
    }

    private ImageUrl getImageOfUser(ProductDto product){
        User user = product.getUser();
        ProfileResponseDto profile = getProfile(user.getId());
        return ImageUrl.of(profile.getImage());
    }

    private String getUserName(ProductDto product){

        return product.getUser().getNickname();
    }

    // 로깅 서비스 호출 메서드 부분(시작)
    private void loggingProductId(Long productId){
        productLogHandler.loggingProductIdData(productId);
    }
    private void loggingQuery(String query, String detail){
        productLogHandler.loggingQueryData(query,detail);
    }
    private void loggingReturnedProduct(ProductDto productDto){
        productLogHandler.loggingReturnedProductData(productDto);
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
