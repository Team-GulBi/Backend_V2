package com.gulbi.Backend.domain.rental.product.service.image;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gulbi.Backend.domain.rental.product.dto.product.ProductMainImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.entity.Image;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.factory.ImageFactory;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrls;
import com.gulbi.Backend.domain.rental.product.vo.image.Images;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageFiles;
import com.gulbi.Backend.global.util.S3Uploader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//fix Todo: 상위 클레스를 호출해서 로직을 처리하니까 ProductResolver는 최상단에만 두고 추후에는 ProductId만 넘겨서 중복되지 않도록 수정.
//todo: resolveProduct가 여기서도 쓰이는데. ProductId를 crud에 넘기도록해서 그쪽에서 조회하도록 추후 변경 할 것. deleteimage 유효성검사 추가 할 것.
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final S3Uploader s3Uploader;
    private final ImageRepoService imageRepoService;

    @Override
    public ImageUrls uploadProductImagesToS3(ProductImageFiles productImageFiles) {
        try {
            List<ImageUrl> imageUrlList = new ArrayList<>();
            for (MultipartFile file : productImageFiles.getProductImages()) {
                ImageUrl imageUrl = ImageUrl.of(s3Uploader.uploadFile(file, "images"));
                imageUrlList.add(imageUrl);
            }
            return ImageUrls.of(imageUrlList);
        } catch (IOException e) {
            throw new RuntimeException("임시코드 입니다");
        }
    }
    @Override
    public Images createImages(ImageUrls imageUrls, Product product) {
        Images images = ImageFactory.createImages(imageUrls, product);
        return images;
    }
    @Override
    public Image createMainImage(ImageUrl imageUrl, Product product){
        Image image = ImageFactory.createMainImage(imageUrl, product);
        return image;
    }

    @Override
    public void changeMainImage(ProductMainImageUpdateCommand productMainImageUpdateCommand) {
        //ToDo: 플래그를 한번 싹 밀고, update하기.
        // private final Long productId;
        // private final ImageUrl mainImageUrl;

        //ToDo: 한번 싹 밀고
        Long productId = productMainImageUpdateCommand.getProductId();
        imageRepoService.updateMainFalseByProductId(productId);
        //ToDo: 해당 url을 가진 이미지를 메인으로 변경
        String url = productMainImageUpdateCommand.getMainImageUrl().getImageUrl();
        imageRepoService.updateMainTrueByUrl(url);
    }
}
