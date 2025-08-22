package com.gulbi.Backend.domain.contract.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

import com.gulbi.Backend.domain.contract.application.dto.ApplicationCalendarResponse;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationDayResponse;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusDetailResponse;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusResponse;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.application.exception.ApplicationException;
import com.gulbi.Backend.domain.contract.application.repository.ApplicationRepoService;
import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.contract.contract.service.ContractService;
import com.gulbi.Backend.domain.contract.contract.service.ContractTemplateRepoService;
import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.rental.product.service.product.crud.ProductRepoService;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {
    private final ApplicationRepoService applicationRepoService;
    private final ContractService contractService;
    private final ProductRepoService productRepoService;
    private final ContractTemplateRepoService contractTemplateRepoService;
    private final UserService userService;


    public void createApplication(Long productId, ApplicationCreateRequest dto) {
        User user = userService.getAuthenticatedUser();
        System.out.println(user.getId());
        System.out.println(user.getId());
        System.out.println(user.getId());
        System.out.println(user.getId());
        ContractTemplate template = contractTemplateRepoService.findByProductIdWithProduct(productId);
        Product product = template.getProduct();

        //예약 접수
        Application application = new Application(product, user, dto.getStartDate(), dto.getEndDate());
        applicationRepoService.save(application);

        //계약서 초안생성
        contractService.createContractFromApplication(application,template);

    }

    public ApplicationCalendarResponse getApplicationsByYearMonth(YearMonth yearMonth, Long productId){
        try {
        //년월에서 해당 월에 첫번째일 즉 1일을 넣어서
        LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
        //해당 년원에 마지막 일을
        LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(LocalTime.MAX);
        //Native쿼리는 DTO맵핑이 바로 불가능 하기 때문에, Projection -> dto 변환 방식 사용
        //Projection이랑 Dto랑 로직적으로 강한 결합이 있기 때문에, Dto안에 from을 두어서 변환하도록 하였음.
        List<ApplicationStatusResponse> status = ApplicationStatusResponse.from(applicationRepoService.findReservationStatusByMonth(productId, startOfMonth, endOfMonth));
        status.stream().forEach(item -> System.out.println(item.toString()));

        //productId를 기반으로 상품을 조회하고 거기에 있는 유저 정보를 뽑아냄. JWT 유저와 비교했을때 같으면 오너 아니면 게스트
        User authenticatedUser = userService.getAuthenticatedUser(); // 로그인한 유저
        Product product = productRepoService.findProductById(productId);
        User productOwner = product.getUser(); // 상품 등록자

        ApplicationCalendarResponse response = new ApplicationCalendarResponse(status,isOwner(authenticatedUser, productOwner));
        return response;
        }catch (ApplicationException e){
            return new ApplicationCalendarResponse(Collections.emptyList(), false);
        }
    }

    public ApplicationDayResponse getApplicationsByDate(LocalDate date, Long productId){
        try{
        User authenticatedUser = userService.getAuthenticatedUser(); // 로그인한 유저
        Product product = productRepoService.findProductById(productId);
        User productOwner = product.getUser();
        List<ApplicationStatusDetailResponse> status = applicationRepoService.findByProductIdAndDate(productId, date);
        ApplicationDayResponse response = new ApplicationDayResponse(status,isOwner(authenticatedUser, productOwner));
        return response;}catch (ApplicationException e){
            return new ApplicationDayResponse(Collections.emptyList(), false);
        }
    }


    private boolean isOwner(User authenticatedUser, User productOwner){
        if(authenticatedUser.getId().equals(productOwner.getId())){
            return true;
        }
        else
            return false;
    }
}
