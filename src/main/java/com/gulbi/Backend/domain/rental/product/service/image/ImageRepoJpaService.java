package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.code.ImageErrorCode;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.exception.ImageException;
import com.gulbi.Backend.domain.rental.product.repository.ImageRepository;
import com.gulbi.Backend.domain.rental.product.vo.Images;
import com.gulbi.Backend.global.error.DatabaseException;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.InfraErrorCode;

import java.util.List;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageRepoJpaService implements ImageRepoService {
    private final String className = this.getClass().getName();
    private final ImageRepository imageRepository;

    @Override
    public void saveAll(List<Image> images) {
        try {
            imageRepository.saveAll(images);
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
                PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(images, className, exception, InfraErrorCode.DB_EXCEPTION));
        }

    }

    @Override
    public void save(Image image){
        imageRepository.save(image);
    }

    @Override
    public Images findImagesByProductId(Long productId) {
        try {
            List<Image> list = imageRepository.findImagesByProductId(productId);
            if(list.isEmpty()){
                throw new ImageException(ExceptionMetaDataFactory.of(productId,className,null, ImageErrorCode.IMAGE_NOT_FOUND));
            }
			return Images.of(list);
        } catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }


    @Override
    public void deleteByIds(List<Long> imageIds) {
        try {
            imageRepository.deleteByIds(imageIds);
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(imageIds, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }


    @Override
    public void removeAllImagesByProductId(Long productId) {
        try {
            imageRepository.deleteAllImagesByProductId(productId);
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }

    }

    @Override
    public void updateMainFalseByProductId(Long productId){
        try{
        imageRepository.updateMainFalseByProductId(productId);
        } catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(productId, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }
    @Override
    public void updateMainTrueByUrl(String url){
        try {
            imageRepository.updateMainTrueByUrl(url);
        }catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception) {
            throw new DatabaseException(ExceptionMetaDataFactory.of(url, className, exception, InfraErrorCode.DB_EXCEPTION));
        }
    }




}
