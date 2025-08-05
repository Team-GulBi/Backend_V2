// package com.gulbi.Backend.domain.rental.application.controller;
//
// import com.gulbi.Backend.domain.rental.application.code.ApplicationSuccessCode;
// import com.gulbi.Backend.domain.rental.application.dto.ApplicationCreateRequest;
// import com.gulbi.Backend.domain.rental.application.service.ApplicationService;
// import com.gulbi.Backend.global.response.RestApiResponse;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// @RestController
// @RequestMapping("api/v1/products/{productId}/applications")
// public class ProductApplicationController {
//
//     private final ApplicationService applicationService;
//
//     public ProductApplicationController(ApplicationService applicationService) {
//         this.applicationService = applicationService;
//     }
//
//     @PostMapping
//     public ResponseEntity<RestApiResponse> createApplication(@PathVariable("productId") Long productId,
//                                                              @RequestBody ApplicationCreateRequest dto) {
//         applicationService.createApplication(productId, dto);
//         RestApiResponse response = new RestApiResponse(ApplicationSuccessCode.APPLICATION_CREATE_SUCCESS);
//         return ResponseEntity.ok(response);
//     }
//
// }
