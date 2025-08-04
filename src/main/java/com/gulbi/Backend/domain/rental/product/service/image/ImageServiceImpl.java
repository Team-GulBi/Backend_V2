package com.gulbi.Backend.domain.rental.product.service.image;

import java.util.List;

import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.dto.product.update.ProductMainImageUpdateDto;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.image.strategy.ImageUpdateStrategy;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductCrudService;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrlCollection;
import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//fix Todo: 상위 클레스를 호출해서 로직을 처리하니까 ProductResolver는 최상단에만 두고 추후에는 ProductId만 넘겨서 중복되지 않도록 수정.
//todo: resolveProduct가 여기서도 쓰이는데. ProductId를 crud에 넘기도록해서 그쪽에서 조회하도록 추후 변경 할 것. deleteimage 유효성검사 추가 할 것.
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageCrudService imageCrudService;
    private final ProductCrudService productCrudService;
    private final List<ImageUpdateStrategy> imageUpdateStrategies;
    @Override
    public void updateProductImages(ProductImageUpdateCommand command) {
        imageUpdateStrategies.stream()
            .filter(strategy->strategy.canUpdate(command))
            .forEach(strategy->strategy.update(command));
    }
}
