package com.gulbi.Backend.domain.rental.product.controller;

import com.gulbi.Backend.domain.rental.product.code.CategorySuccessCode;
import com.gulbi.Backend.domain.rental.product.dto.CategoryProjection;
import com.gulbi.Backend.domain.rental.product.service.category.CategoryRepoService;
import com.gulbi.Backend.global.response.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepoService categoryRepoService;


    @GetMapping("/bcategory")
    @Operation(
            summary = "모든 대분류 조회 ",
            description = "모든 대분류 카테고리를 조회"
    )

    public ResponseEntity<RestApiResponse> getBigCategory(){
        List<CategoryProjection> list = categoryRepoService.findAllBigCategories();
        RestApiResponse response = new RestApiResponse(CategorySuccessCode.GET_CATEGORY_SUCCESS,list);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/mcategory/{categoryId}")
    @Operation(
            summary = "대분류, 중분류 카테고리 조회",
            description = "대분류, 중분류 카테고리 조회, EX) 대분류 카테고리 id = 1 일때 해당 api를 사용하면 1에 대응 하는 중분류를 보여줌"
    )
    public ResponseEntity<RestApiResponse> getMidCategory(@PathVariable("categoryId") Long categoryId){
        List<CategoryProjection> list = categoryRepoService.findAllBelowByParentId(categoryId);
        RestApiResponse response = new RestApiResponse(CategorySuccessCode.GET_CATEGORY_SUCCESS,list);
        return ResponseEntity.ok(response);
    }
    //1. 대분류 전부 리턴, 2. 대분류의 id값 들어오면 대응하는 모든 중분류 보여주기 3. 중분류의 id값이 들어오면 대응하는 모든 소분류 보여주기
}
