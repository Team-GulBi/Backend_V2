//package com.gulbi.Backend.domain.rental.product.dto.product.request;
//
//import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
//import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
//import com.gulbi.Backend.domain.user.entity.User;
//import lombok.Getter;
//
//@Getter
//public class ProductRegisterDto {
//
//    private final String tag;
//    private final String title;
//    private final String name;
//    private final Integer price;
//    private final String sido;
//    private final String sigungu;
//    private final String bname;
//    private final String description;
//    private final CategoryInProductDto categories;
//    private final User user;
//
//    private ProductRegisterDto(ProductRegisterRequestDto productRegisterRequest,
//                               CategoryInProductDto categoryInProduct,
//                               User user) {
//        this.tag = productRegisterRequest.getTag();
//        this.title = productRegisterRequest.getTitle();
//        this.name = productRegisterRequest.getName();
//        this.price = productRegisterRequest.getPrice();
//        this.sido = productRegisterRequest.getSido();
//        this.sigungu = productRegisterRequest.getSigungu();
//        this.bname = productRegisterRequest.getBname();
//        this.description = productRegisterRequest.getDescription();
//        this.categories = categoryInProduct;
//        this.user = user;
//    }
//
//    public static ProductRegisterDto of(ProductRegisterRequestDto productRegisterRequestDto,
//                                        CategoryInProductDto categoryInProduct,
//                                        User user) {
//        return new ProductRegisterDto(productRegisterRequestDto, categoryInProduct, user);
//    }
//}
