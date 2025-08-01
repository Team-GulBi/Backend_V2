package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.code.ImageErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageDto;
import com.gulbi.Backend.domain.rental.product.dto.ProductImageDtoCollection;
import com.gulbi.Backend.domain.rental.product.dto.product.request.ProductImageDeleteRequestDto;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.exception.ImageException;
import com.gulbi.Backend.domain.rental.product.factory.ImageFactory;
import com.gulbi.Backend.domain.rental.product.repository.ImageRepository;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.util.S3Uploader;
import jakarta.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageCrudServiceImpl implements ImageCrudService {
    private final String className = this.getClass().getName();
    private final ImageRepository imageRepository;
    private final ProductCrudService productCrudService;
    private final S3Uploader s3Uploader;

    @Override
    public void registerImagesWithProduct(ImageUrlCollection imageUrlCollection, Product product) {
        ImageCollection imageCollection = ImageFactory.createImages(imageUrlCollection, product);
        saveImages(imageCollection);
    }

    @Override
    public ImageUrlCollection uploadImagesToS3(ProductImageCollection productImageCollection) {
        try {
            List<ImageUrl> imageUrlList = new ArrayList<>();
            for (MultipartFile file : productImageCollection.getProductImages()) {
                ImageUrl imageUrl = ImageUrl.of(s3Uploader.uploadFile(file, "images"));
                imageUrlList.add(imageUrl);
            }
            return ImageUrlCollection.of(imageUrlList);
        } catch (ImageException e) {
            throw createImageUploadException(productImageCollection, e);
        } catch (IOException e) {
            throw createImageProcessingException(productImageCollection, e);
        }
    }

    @Override
    public void updateMainImageFlags(ProductMainImageUpdateDto productMainImageUpdateDto) {
        imageRepository.updateImagesFlagsToTrue(productMainImageUpdateDto.getMainImageUrl().getImageUrl());
    }

    @Override
    public ProductImageDtoCollection getImageByProductId(Long productId) {
        resolveProduct(productId);
        List<ProductImageDto> images = imageRepository.findByImageWithProduct(productId);
        return ProductImageDtoCollection.of(images);
    }

    @Override
    public void saveMainImage(ImageUrl mainImageUrl, Product product) {
        imageRepository.save(ImageFactory.createImageToMain(mainImageUrl, product));
    }

    @Override
    public void clearMainImageFlags(Product product) {
        imageRepository.resetMainImagesByProduct(product);
    }

    @Override
    public void saveImages(ImageCollection imageCollection) {
        try {
            imageRepository.saveAll(imageCollection.getImages());
        } catch (DataIntegrityViolationException | JpaSystemException | PersistenceException |
                 IllegalArgumentException e) {
            throw createImageUploadException(imageCollection, e);
        }
    }

    @Override
    public void deleteImages(ProductImageDeleteRequestDto productImageDeleteRequestDto) {
        if (productImageDeleteRequestDto.getImagesId() == null) {
            throw createImageDeleteValidationException(productImageDeleteRequestDto);
        }

        try {
            imageRepository.deleteImages(productImageDeleteRequestDto);
        } catch (DataIntegrityViolationException e) {
            throw createImageDeleteValidationException(productImageDeleteRequestDto, e);
        } catch (JpaSystemException | PersistenceException e) {
            throw createImageDatabaseErrorException(productImageDeleteRequestDto, e);
        } catch (IllegalArgumentException e) {
            throw createInvalidProductImageIdException(productImageDeleteRequestDto, e);
        } catch (Exception e) {
            throw createImageDeleteFailedException(productImageDeleteRequestDto, e);
        }
    }


    @Override
    public void removeAllImagesFromProduct(Long productId) {
        imageRepository.deleteAllImagesByProductId(resolveProduct(productId));
    }

    private Product resolveProduct(Long productId) {
        return productCrudService.getProductById(productId);
    }

    private ImageException.NotUploadImageToS3Exception createImageUploadException(Object args, Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ImageErrorCode.CANT_UPLOAD_IMAGE_TO_S3)
                .build();
        throw new ImageException.NotUploadImageToS3Exception(exceptionMetaData);
    }

    private ImageException.ImageDeleteValidationException createImageDeleteValidationException(Object args,
                                                                                               Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ImageErrorCode.IMAGE_DELETE_FAILED)
                .build();
        throw new ImageException.ImageDeleteValidationException(exceptionMetaData);
    }

    private ImageException.ImageDeleteValidationException createImageDeleteValidationException(Object args) {
        //
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .responseApiCode(ImageErrorCode.INVALID_IMAGE_ID)
                .build();
        throw new ImageException.ImageDeleteValidationException(exceptionMetaData);
    }

    private ImageException.DatabaseErrorException createImageDatabaseErrorException(Object args, Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ImageErrorCode.DATABASE_ERROR)
                .build();
        throw new ImageException.DatabaseErrorException(exceptionMetaData);
    }

    private ImageException.InvalidProductImageIdException createInvalidProductImageIdException(Object args,
                                                                                               Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ImageErrorCode.INVALID_IMAGE_ID)
                .build();
        throw new ImageException.InvalidProductImageIdException(exceptionMetaData);
    }

    private ImageException.ImageDeleteFailedException createImageDeleteFailedException(Object args, Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(className)
                .stackTrace(e)
                .responseApiCode(ImageErrorCode.IMAGE_DELETE_FAILED).build();
        throw new ImageException.ImageDeleteFailedException(exceptionMetaData);
    }

    private RuntimeException createImageProcessingException(Object args, Exception e) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData.Builder().args(args).className(className)
                .stackTrace(e).responseApiCode(ImageErrorCode.CANT_UPLOAD_IMAGE_TO_S3).build();
        throw new ImageException.NotUploadImageToS3Exception(exceptionMetaData);
    }
}
