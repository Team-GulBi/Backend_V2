//package com.gulbi.Backend.domain.rental.product.dto.product;
//
//import com.gulbi.Backend.domain.rental.product.dto.category.CategoryInProductDto;
//import com.gulbi.Backend.domain.rental.product.dto.product.request.register.ProductRegisterRequestDto;
//import com.gulbi.Backend.domain.rental.product.entity.Category;
//import com.gulbi.Backend.domain.rental.product.vo.image.ProductImageCollection;
//import com.gulbi.Backend.domain.user.entity.User;
//import lombok.Getter;
//
//@Getter
//public class ProductRegisterDto {
//    private User user;
//    private String tag;
//    private String title;
//    private String productName;
//    private Integer price;
//    private String sido;
//    private String sigungu;
//    private String bname;
//    private String description;
//    private Category bCategory;
//    private Category mCategory;
//    private Category cCategory;
//    private ProductImageCollection productImageCollection;
//
//    public ProductRegisterDto(String tag, String title, String productName, Integer price, String sido, String sigungu, String bname, String description) {
//        this.tag = tag;
//        this.title = title;
//        this.productName = productName;
//        this.price = price;
//        this.sido = sido;
//        this.sigungu = sigungu;
//        this.bname = bname;
//        this.description = description;
//    }
//
//    public static ProductRegisterDto of(ProductRegisterRequestDto product, CategoryInProductDto categoryInProduct, User user) {
//        return new ProductRegisterDto(
//                product.getTag(),
//                product.getTitle(),
//                product.getName(),
//                product.getPrice(),
//                product.getSido(),
//                product.getSigungu(),
//                product.getBname(),
//                product.getDescription()
//        );
//    }
//}