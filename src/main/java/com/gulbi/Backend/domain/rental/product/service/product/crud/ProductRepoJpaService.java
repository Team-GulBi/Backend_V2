package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import jakarta.persistence.PersistenceException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class ProductRepoJpaService implements ProductRepoService {
    private final String className = this.getClass().getName();
    private final ProductRepository productRepository;

    @Override
    public Long save(Product product) {
        Long savedProductId = null;
        try {
            savedProductId = productRepository.save(product).getId();
        } catch (DataIntegrityViolationException e) {
            createProductValidationException(product, e);
        } catch (JpaSystemException | PersistenceException e) {
            createDatabaseErrorException(product, e);
        } catch (IllegalArgumentException e) {
            createMissingProductFieldException(product, e);
        }
        return savedProductId;
    }

    @Override
    public Product findProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findProductById(productId);
        if (!productOptional.isPresent()) {
            createProductNotFoundExceptionWithMetaData(productId);  // 예외 던지기
        }
        return productOptional.get();  // 값이 있으면 반환
    }

    @Override
    public Product findProductByIdWithUser(Long productId) {
        //ToDo: 예외처리
        return productRepository.findByIdWithAll(productId).orElseThrow();
    }

    //조회 성능을 위해 단순조회는 Projection 사용
    @Override
    public List<ProductOverViewResponse> findProductOverViewByTitle(String title) {
        List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByTitle(title);
        if (overViewResponses.isEmpty()) {
            createNoProductFoundForTitleException(title);
        }
        return overViewResponses;
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByproductIds(List<Long> productIds) {
        List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByIds(productIds);
        if (overViewResponses.isEmpty()) {
            createNoProductFoundForTitleException(productIds);
        }
        return overViewResponses;
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt) {
        List<ProductOverViewResponse> overViewResponses =productRepository.findAllProductOverviewsByCreatedAtDesc(lastCreatedAt,pageable);
        if (overViewResponses.isEmpty()) {
            createNoProductFoundForTitleException(null);
        }
        return overViewResponses;
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable) {
        return productRepository.findAllProductByCategoryIds(bCategoryId, mCategoryId, sCategoryId,lastCreatedAt,pageable);
    }


    @Override
    public List<ProductOverViewResponse> findProductOverViewByTag(String tag, String tag2, String tag3) {
        List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByTag(tag, tag2, tag3);
        if (overViewResponses.isEmpty()) {
            createNoProductFoundForTagsException(tag, tag2, tag3);
        }
        return overViewResponses;
    }



    @Override
    public void delete(Long productId) {
        productRepository.deleteAllbyId(productId);
    }



    private void createProductValidationException(Object args, Throwable e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ProductErrorCode.PRODUCT_VALIDATION_FAILED)
                .build();
        throw new ProductException.ProductValidationException(exceptionMetaData);
    }

    private void createDatabaseErrorException(Object args, Throwable e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ProductErrorCode.DATABASE_ERROR)
                .build();
        throw new ProductException.DatabaseErrorException(exceptionMetaData);
    }

    private void createMissingProductFieldException(Object args, Throwable e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ProductErrorCode.MISSING_REQUIRED_FIELD)
                .build();
        throw new ProductException.MissingProductFieldException(exceptionMetaData);
    }

    private void createProductNotFoundException(Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .responseApiCode(ProductErrorCode.PRODUCT_NOT_FOUND)
                .build();

        throw new ProductException.ProductNotFoundException(exceptionMetaData);
    }

    private void createProductNotFoundExceptionWithMetaData(Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .responseApiCode(ProductErrorCode.PRODUCT_NOT_FOUND)
                .build();
        throw new ProductException.ProductNotFoundException(exceptionMetaData);
    }

    private void createNoProductFoundForTitleException(Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .responseApiCode(ProductErrorCode.PRODUCT_NOT_FOUND_BY_TITLE)
                .build();
        throw new ProductException.NoProductFoundForTitleException(exceptionMetaData);
    }

    private void createNoProductFoundForTagsException(String tag, String tag2, String tag3) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(tag + ", " + tag2 + ", " + tag3)
                .className(className)
                .responseApiCode(ProductErrorCode.PRODUCT_NOT_FOUND_BY_TAGS)
                .build();
        throw new ProductException.NoProductFoundForTagsException(exceptionMetaData);
    }

    private void createNoUpdateProductException(Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .responseApiCode(ProductErrorCode.NO_UPDATED_COLUMNS)
                .build();
        throw new ProductException.NoUpdateProductException(exceptionMetaData);
    }
}
