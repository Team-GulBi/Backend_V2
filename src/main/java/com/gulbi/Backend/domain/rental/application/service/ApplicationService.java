package com.gulbi.Backend.domain.rental.application.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.List;
import com.gulbi.Backend.domain.rental.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.domain.rental.application.dto.ApplicationStatusResponseDto;
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

    // ToDo: 유효성 검사는 그대로 두되
    public Application createApplication(Long productId, ApplicationCreateRequest dto) {

        Long userId = jwtUtil.extractUserIdFromRequest();

        // => ToDo: 유효성 검사
        User user = userRepository //빌리려는 사람 엔티티
            .findById(userId)
            .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 상품입니다."));

        Application application = new Application(product, user, dto.getStartDate(), dto.getEndDate());

        return applicationRepository.save(application);
    }

    public List<ApplicationStatusResponseDto> getApplicationsByYearMonth(YearMonth yearMonth, Long productId){
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        //Native쿼리는 DTO맵핑이 바로 불가능 하기 때문에, Projection -> dto 변환 방식 사용
        //Projection이랑 Dto랑 로직적으로 강한 결합이 있기 때문에, Dto안에 from을 두어서 변환하도록 하였음.
        List<ApplicationStatusResponseDto> status = ApplicationStatusResponseDto.from(applicationRepository.findReservationStatusByMonth(productId, startOfMonth, endOfMonth));
        status.stream().forEach(item -> System.out.println(item.toString()));
        return status;
    }
}
