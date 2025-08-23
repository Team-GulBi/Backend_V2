package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequest;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.product.delete.ProductDeleteService;
import com.gulbi.Backend.domain.rental.product.service.product.register.ProductRegistrationService;
import com.gulbi.Backend.domain.rental.product.service.product.search.ProductSearchService;
import com.gulbi.Backend.domain.rental.product.service.product.update.ProductUpdatingService;
import com.gulbi.Backend.global.CursorPageable;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
// 해당 클래스는 클라이언트 서비스 만을 노출
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRegistrationService productRegistrationService;
    private final ProductSearchService productSearchService;
    private final ProductUpdatingService productUpdatingService;
    private final ProductDeleteService productDeleteService;
    private final Logger LOGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    // 상품 대략 조회
    @Override
    public ProductOverviewSlice searchProductOverview(ProductSearchRequest productSearchRequest, CursorPageable cursorPageable) {
        return productSearchService.searchProductByQuery(productSearchRequest,cursorPageable);
    }

    // 상품 등록
    @Override
    public Long registrationProduct(ProductRegisterCommand command){
        return productRegistrationService.registerProduct(command);
    }

    // 특정 상품 상세 조회
    @Override
    public ProductDetailResponse getProductDetail(Long productId){
        return productSearchService.getProductDetail(productId);
    }

    @Override
    public ProductOverviewSlice getAllUserProducts(Long userId,CursorPageable cursorPageable) {
        return productSearchService.getUserProducts(userId, cursorPageable);
    }

    @Override
    public ProductOverviewSlice getAllProducts(CursorPageable cursorPageable) {
        return productSearchService.getMyProducts(cursorPageable);
    }

    // 상품 업데이트
    @Override
    public void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand){
        productUpdatingService.updateProductInfo(productContentUpdateCommand, productImageUpdateCommand);
    }

    // 상품 삭제
    @Override
    public void deleteProduct(Long productId) {
        productDeleteService.deleteProductInfo(productId);
    }

}
