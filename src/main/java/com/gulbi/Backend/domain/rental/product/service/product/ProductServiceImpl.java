package com.gulbi.Backend.domain.rental.product.service.product;

import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.ProductRegisterCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductDetailResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.ProductContentUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.product.delete.ProductDeleteService;
import com.gulbi.Backend.domain.rental.product.service.product.register.ProductRegistrationService;
import com.gulbi.Backend.domain.rental.product.service.product.search.ProductSearchService;
import com.gulbi.Backend.domain.rental.product.service.product.update.ProductUpdatingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRegistrationService productRegistrationService;
    private final ProductSearchService productSearchService;
    private final ProductUpdatingService productUpdatingService;
    private final ProductDeleteService productDeleteService;
    private final Logger LOGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public List<ProductOverViewResponse> searchProductOverview(ProductSearchRequestDto productSearchRequestDto) {
        return productSearchService.searchProductByQuery(productSearchRequestDto);
    }
    @Override
    public Long registrationProduct(ProductRegisterCommand command){

        return productRegistrationService.registerProduct(command);
    }
    @Override
    public ProductDetailResponse getProductDetail(Long productId){
        return productSearchService.getProductDetail(productId);
    }

    @Override
    public void updateProductViews(Long productId) {
        productUpdatingService.updateProductViews(productId);
    }

    @Override
    public void updateProduct(ProductContentUpdateCommand productContentUpdateCommand, ProductImageUpdateCommand productImageUpdateCommand){
        productUpdatingService.updateProductInfo(productContentUpdateCommand, productImageUpdateCommand);
    }

    @Override
    public void deleteProduct(Long productId) {
        productDeleteService.deleteProductInfo(productId);
    }

}
