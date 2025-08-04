package com.gulbi.Backend.domain.rental.application.service;

import com.gulbi.Backend.domain.rental.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.domain.rental.application.entity.Application;
import com.gulbi.Backend.domain.rental.application.repository.ApplicationRepository;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import com.gulbi.Backend.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    public void createApplication(Long productId, ApplicationCreateRequest dto) {

        Long userId = jwtUtil.extractUserIdFromRequest();
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        Application application = new Application(product, user, dto.getStartDate(), dto.getEndDate());

        applicationRepository.save(application);
    }
}
