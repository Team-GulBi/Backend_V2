package com.gulbi.Backend.global.test;

import com.gulbi.Backend.domain.rental.product.entity.Category;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.repository.ProductRepository;
import com.gulbi.Backend.domain.rental.review.entity.Review;
import com.gulbi.Backend.domain.rental.review.repository.ReviewRepository;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductDataInitService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductDummyService productDummyService;
    private final ReviewRepository reviewRepository;

    private final UserInitService userInitService;
    private final ProfileInitService profileInitService;

    public void run() throws Exception {
        long startTime = System.currentTimeMillis();
        userInitService.createDummyUser();
        profileInitService.initProfile();
        List<Product> products = new ArrayList<>();
        List<Review> reviews = new ArrayList<>();
        List<User> testUsers = userRepository.findAll();
        for (User user : testUsers) {
            for (int i = 0; i < 10; i++) {
                String productName = productDummyService.getRandomProductName();
                String[] region = productDummyService.getRandomRegion();
                String sido = region[0];
                String sigungu = region[1];
                List<Category> categories = productDummyService.getRandomCategory();
                Category bcategory = categories.get(0);
                Category mcategory = categories.get(1);
                Category scategory = categories.get(2);
                String tags = productDummyService.getRandomTag();
                Integer price = productDummyService.getRandomPrice();

                Product product = Product.builder()
                        .user(user)
                        .name(productName)
                        .bCategory(bcategory)
                        .mCategory(mcategory)
                        .sCategory(scategory)
                        .sido(sido)
                        .sigungu(sigungu)
                        .bname("동동")
                        .description("야무짐")
                        .title(productName)
                        .tag(tags)
                        .mainImage("https://test.com")
                        .price(price)
                        .views(0)
                        .build();

                products.add(product);
                productDummyService.setRandomReview(product, user);

            }
        }

        productRepository.saveAll(products);
        reviewRepository.saveAll(ProductDummyService.getReviews());

        long endTime = System.currentTimeMillis(); // 종료 시간 기록
        long duration = endTime - startTime; // 실행 시간 계산

    }

}
