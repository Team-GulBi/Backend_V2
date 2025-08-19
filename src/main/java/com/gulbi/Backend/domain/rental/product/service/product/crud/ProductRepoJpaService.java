package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.global.error.DatabaseException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.InfraErrorCode;

import lombok.RequiredArgsConstructor;

import org.springframework.dao.EmptyResultDataAccessException;
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
    public Product save(Product product) {
            try {
                return productRepository.save(product);
            }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
                throw new DatabaseException(ExceptionMetaDataFactory.of(product, className, exception, InfraErrorCode.DB_EXCEPTION)
        );//InfrastructureException
    }


    }

    @Override
    public Product findProductById(Long productId) {
        try {
            Optional<Product> productOptional = productRepository.findProductById(productId);
            if (!productOptional.isPresent()) {
                throw new ProductException(ExceptionMetaDataFactory.of(productId,className,null,ProductErrorCode.PRODUCT_NOT_FOUND));
            }
            return productOptional.get();  // 값이 있으면 반환
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public Product findProductByIdWithUser(Long productId) {
        try {
            return productRepository.findByIdWithAll(productId)
                .orElseThrow(() -> new ProductException(
                    ExceptionMetaDataFactory.of(productId, className, null, ProductErrorCode.PRODUCT_NOT_FOUND)
                ));
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    //조회 성능을 위해 단순조회는 Projection 사용
    @Override
    public List<ProductOverViewResponse> findProductOverViewByTitle(String title) {
        try {
            List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByTitle(title);
            if (overViewResponses.isEmpty()) {
                throw new ProductException(
                    ExceptionMetaDataFactory.of(title, className, null, ProductErrorCode.PRODUCT_NOT_FOUND_BY_TITLE));
            }
            return overViewResponses;
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(title, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByproductIds(List<Long> productIds) {
        try {
            List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByIds(productIds);
            if (overViewResponses.isEmpty()) {
            throw new ProductException(ExceptionMetaDataFactory
                .of(productIds, className, null,ProductErrorCode.PRODUCT_NOT_FOUND));
            }
            return overViewResponses;
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productIds, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt) {
        List<ProductOverViewResponse> overViewResponses =productRepository.findAllProductOverviewsByCreatedAtDesc(lastCreatedAt,pageable);
        //ToDo: 연동 시작 하면사 바뀔 수 있으므로 일단 패스.
        // if (overViewResponses.isEmpty()) {
        //     createNoProductFoundForTitleException(null);
        // }
        return overViewResponses;
    }

    @Override
    public List<ProductOverViewResponse> findProductOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable) {
        //ToDo: 연동 시작 하면사 바뀔 수 있으므로 일단 패스.
        return productRepository.findAllProductByCategoryIds(bCategoryId, mCategoryId, sCategoryId,lastCreatedAt,pageable);
    }


    @Override
    //ToDo: 태그 검색은 보류(사용 안함.
    public List<ProductOverViewResponse> findProductOverViewByTag(String tag, String tag2, String tag3) {
        List<ProductOverViewResponse> overViewResponses = productRepository.findProductsByTag(tag, tag2, tag3);
        // if (overViewResponses.isEmpty()) {
        //     createNoProductFoundForTagsException(tag, tag2, tag3);
        // }
        return overViewResponses;
    }


    @Override
    public void delete(Long productId) {
        productRepository.deleteAllbyId(productId);
    }
}
