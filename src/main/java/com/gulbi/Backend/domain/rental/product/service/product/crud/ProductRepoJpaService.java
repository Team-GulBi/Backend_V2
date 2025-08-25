package com.gulbi.Backend.domain.rental.product.service.product.crud;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverViewResponse;
import com.gulbi.Backend.domain.rental.product.dto.ProductOverviewSlice;
import com.gulbi.Backend.domain.rental.product.dto.ProductSearchCondition;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.repository.ProductCustomRepository;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.global.CursorPageable;
import com.gulbi.Backend.global.error.DatabaseException;
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
    private final ProductCustomRepository productCustomRepository;

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
    public Product findById(Long productId) {
        try {
            Optional<Product> productOptional = productRepository.findById(productId);
            if (!productOptional.isPresent()) {
                throw new ProductException(ExceptionMetaDataFactory.of(productId,className,null,ProductErrorCode.PRODUCT_NOT_FOUND));
            }
            return productOptional.get();  // 값이 있으면 반환
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public Product findByIdWithUser(Long productId) {
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
    public ProductOverviewSlice findOverViewByTitle(ProductSearchCondition title, CursorPageable cursorPageable) {
        try {
            ProductOverviewSlice overViewResponses = productCustomRepository.findAllByCursor(cursorPageable,title);
            if (overViewResponses.getProducts().isEmpty()) {
                throw new ProductException(
                    ExceptionMetaDataFactory.of(title, className, null, ProductErrorCode.PRODUCT_NOT_FOUND_BY_TITLE));
            }
            return overViewResponses;
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(title, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }

    @Override
    public ProductOverviewSlice findOverViewByUser(ProductSearchCondition user, CursorPageable pageable) {
        try {
            ProductOverviewSlice overViewResponses = productCustomRepository.findAllByCursor(pageable, user);
            if (overViewResponses.getProducts().isEmpty()) {
                throw new ProductException(
                    ExceptionMetaDataFactory.of(user, className, null, ProductErrorCode.PRODUCT_NOT_FOUND_BY_TITLE));
            }
            return overViewResponses;
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(user, className, exception, InfraErrorCode.DB_EXCEPTION));
    }
    }

    @Override
    public List<ProductOverViewResponse> findOverViewByproductIds(List<Long> productIds) {
        try {
            List<ProductOverViewResponse> overViewResponses = productRepository.findAllOverViewByIdIn(productIds);
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
    public List<ProductOverViewResponse> findOverViewByCreatedAtDesc(Pageable pageable, LocalDateTime lastCreatedAt) {
        List<ProductOverViewResponse> overViewResponses =productRepository.findAllOverviewByCreatedAtDesc(lastCreatedAt,pageable);
        //ToDo: 연동 시작 하면사 바뀔 수 있으므로 일단 패스.
        // if (overViewResponses.isEmpty()) {
        //     createNoProductFoundForTitleException(null);
        // }
        return overViewResponses;
    }

    @Override
    public List<ProductOverViewResponse> findOverViewByCategories(Long bCategoryId, Long mCategoryId, Long sCategoryId, LocalDateTime lastCreatedAt, Pageable pageable) {
        //ToDo: 연동 시작 하면사 바뀔 수 있으므로 일단 패스.
        return productRepository.findAllByCategoryIds(bCategoryId, mCategoryId, sCategoryId,lastCreatedAt,pageable);
    }




    @Override
    public void delete(Long productId) {
        productRepository.deleteAllById(productId);
    }
}
