package com.gulbi.Backend.domain.rental.product.service.image;

import java.util.List;

import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.vo.Images;

public interface ImageRepoService {
    void saveAll(List<Image> images);
    void save(Image image);
    Images findImagesByProductId(Long productId);
    void deleteByIds(List<Long> imageIds);
    void removeAllImagesByProductId(Long productId);
    void deleteAllByProduct(Product product);
    void updateMainFalseByProductId(Long productId);
    void updateMainTrueByUrl(String url);

}
