package com.gulbi.Backend.domain.rental.product.service.image;

import java.util.List;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.rental.product.dto.ProductImageUpdateCommand;
import com.gulbi.Backend.domain.rental.product.service.image.strategy.ImageUpdateStrategy;
@Component
public class ImageUpdateService {
	private final List<ImageUpdateStrategy> imageUpdateStrategies;

	public ImageUpdateService(List<ImageUpdateStrategy> imageUpdateStrategies) {
		this.imageUpdateStrategies = imageUpdateStrategies;
	}

	public void updateProductImages(ProductImageUpdateCommand command) {
		imageUpdateStrategies.stream()
			.filter(strategy->strategy.canUpdate(command))
			.forEach(strategy->strategy.update(command));
	}
}
