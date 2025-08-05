package com.gulbi.Backend.domain.rental.application.controller;

import java.time.YearMonth;
import java.util.List;

import com.gulbi.Backend.domain.rental.application.code.ApplicationSuccessCode;
import com.gulbi.Backend.domain.rental.application.dto.ApplicationCreateRequest;
import com.gulbi.Backend.domain.rental.application.dto.ApplicationStatusResponse;
import com.gulbi.Backend.domain.rental.application.service.ApplicationService;
import com.gulbi.Backend.domain.rental.product.code.ProductSuccessCode;
import com.gulbi.Backend.global.response.RestApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products/applications")
public class ProductApplicationController {

    private final ApplicationService applicationService;

    public ProductApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<RestApiResponse> createApplication(@PathVariable("productId") Long productId,
                                                             @RequestBody ApplicationCreateRequest dto) {
        applicationService.createApplication(productId, dto);
        RestApiResponse response = new RestApiResponse(ApplicationSuccessCode.APPLICATION_CREATE_SUCCESS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/calendar/{productId}/{date}")
    public ResponseEntity<RestApiResponse> getApplicatonByCalendar(
        @PathVariable("productId") Long productId,
        @PathVariable("date") YearMonth yearMonth
    ){
        //임시 응답 코드.. ToDo: 응답코드 변경
        List<ApplicationStatusResponse> responseData = applicationService.getApplicationsByYearMonth(yearMonth, productId);
        RestApiResponse response = new RestApiResponse(ProductSuccessCode.PRODUCT_REGISTER_SUCCESS,responseData);
        return ResponseEntity.ok(response);

    }

    // @GetMapping("/dates/{productId}/{date}")
    // public ResponseEntity<RestApiResponse> getApplicationByDate(){
    //
    // }

}
