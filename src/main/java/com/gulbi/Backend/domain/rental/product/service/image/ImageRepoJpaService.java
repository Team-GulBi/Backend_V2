package com.gulbi.Backend.domain.rental.product.service.image;

import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.repository.ImageRepository;
import com.gulbi.Backend.domain.rental.product.vo.Images;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageRepoJpaService implements ImageRepoService {
    private final String className = this.getClass().getName();
    private final ImageRepository imageRepository;
    //ToDo: 해당 계층은 다 만들어진 image만 저장하도록
    @Override
    public void saveAll(List<Image> images) {
        imageRepository.saveAll(images);
    }

    @Override
    public void save(Image image){
        imageRepository.save(image);
    }

    @Override
    public Images findImagesByProductId(Long productId) {
        Images images = Images.of(imageRepository.findImagesByProductId(productId));
        return images;
    }


    @Override
    public void deleteByIds(List<Long> imageIds) {
        imageRepository.deleteByIds(imageIds);
    }


    @Override
    public void removeAllImagesByProductId(Long productId) {
        imageRepository.deleteAllImagesByProductId(productId);
    }

    @Override
    public void updateMainFalseByProductId(Long productId){
        imageRepository.updateMainFalseByProductId(productId);
    }
    @Override
    public void updateMainTrueByUrl(String url){
        imageRepository.updateMainTrueByUrl(url);
    }




}
