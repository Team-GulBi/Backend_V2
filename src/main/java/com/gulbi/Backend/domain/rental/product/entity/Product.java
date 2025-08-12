package com.gulbi.Backend.domain.rental.product.entity;

import com.gulbi.Backend.domain.rental.product.code.ProductErrorCode;
import com.gulbi.Backend.domain.rental.product.dto.category.CategoryBundle;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductCategoryUpdateRequest;
import com.gulbi.Backend.domain.rental.product.dto.product.ProductTextUpdateRequest;
import com.gulbi.Backend.domain.rental.product.exception.ProductException;
import com.gulbi.Backend.domain.rental.product.vo.image.ImageUrl;
import com.gulbi.Backend.domain.user.entity.User;
import com.gulbi.Backend.global.entity.BaseEntity;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products", indexes = @Index(name="idx_created_at",columnList = "created_at DESC"))
@NoArgsConstructor
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 사용자와 다대일 관계 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)  // 대분류와 다대일 관계 설정
    @JoinColumn(name = "b_category_id", nullable = false) // 외래 키 이름 수정
    private Category bCategory;

    @ManyToOne(fetch = FetchType.LAZY)  // 중분류와 다대일 관계 설정
    @JoinColumn(name = "m_category_id", nullable = false) // 외래 키 이름 수정
    private Category mCategory;

    @ManyToOne(fetch = FetchType.LAZY)  // 소분류와 다대일 관계 설정
    @JoinColumn(name = "s_category_id", nullable = false) // 외래 키 이름 수정
    private Category sCategory;

    @Column(length = 100)
    private String tag;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private int views;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false, length = 20)
    private String sido;  // 시/도

    @Column(nullable = false, length = 20)
    private String sigungu;  // 시/군/구

    @Column(length = 20)
    private String bname;  // 읍/면/동

    @Column(nullable = false, length = 500)
    private String description;  // 상품 설명

    @Column(nullable = false, length = 2000)
    private String mainImage;

    // 생성자
    @Builder
    private Product(User user, Category bCategory, Category mCategory, Category sCategory, String tag, String title, String name, int views, int price,
                    String sido, String sigungu, String bname, String description, String mainImage) {

        // 유틸리티 메서드를 사용하여 메타데이터 자동 생성
        if (user == null) {
            throwProductException(ProductErrorCode.MISSING_USER, user);
        }
        if (bCategory == null || mCategory == null || sCategory == null) {
            throwProductException(ProductErrorCode.MISSING_CATEGORY, bCategory);
        }
        if (title == null || title.isEmpty()) {
            throwProductException(ProductErrorCode.MISSING_TITLE, title);
        }
        if (name == null || name.isEmpty()) {
            throwProductException(ProductErrorCode.MISSING_NAME, name);
        }
        if (views < 0) {
            throwProductException(ProductErrorCode.INVALID_VIEWS, views);
        }
        if (price < 0) {
            throwProductException(ProductErrorCode.INVALID_PRICE, price);
        }
        if (sido == null || sido.isEmpty()) {
            throwProductException(ProductErrorCode.MISSING_SIDO, sido);
        }
        if (sigungu == null || sigungu.isEmpty()) {
            throwProductException(ProductErrorCode.MISSING_SIGUNGU, sigungu);
        }
        if (description == null || description.isEmpty()) {
            throwProductException(ProductErrorCode.MISSING_DESCRIPTION, description);
        }

        this.user = user;
        this.bCategory = bCategory;
        this.mCategory = mCategory;
        this.sCategory = sCategory;
        this.tag = tag;
        this.title = title;
        this.name = name;
        this.views = views;
        this.price = price;
        this.sido = sido;
        this.sigungu = sigungu;
        this.bname = bname;
        this.description = description;
        this.mainImage = mainImage;
    }

    private ProductException.MissingProductFieldException throwProductException(ProductErrorCode errorCode, Object args) {
        ExceptionMetaData exceptionMetaData = new ExceptionMetaData
                .Builder()
                .args(args)
                .className(this.getClass().getName())
                .responseApiCode(errorCode)
                .build();
        throw new ProductException.MissingProductFieldException(exceptionMetaData);
    }

    public void updateMainImage(ImageUrl mainImage){
        this.mainImage = mainImage.getImageUrl();
    }

    public void updateView(){
        this.views = this.views + 1;
    }
    public void updateTextInfo(ProductTextUpdateRequest dto) {
        if (dto.getTag() != null) this.tag = dto.getTag();
        if (dto.getTitle() != null) this.title = dto.getTitle();
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getPrice() != null) this.price = dto.getPrice();
        if (dto.getSido() != null) this.sido = dto.getSido();
        if (dto.getSigungu() != null) this.sigungu = dto.getSigungu();
        if (dto.getBname() != null) this.bname = dto.getBname();
        if (dto.getDescription() != null) this.description = dto.getDescription();
    }

    public void updateCategories(CategoryBundle categories) {
        if (categories.getBCategory() != null) {
            // 엔티티에 맞게 Category 객체를 찾아서 할당해야 함 (예: CategoryRepository 사용)
            this.bCategory = categories.getBCategory();
        }
        if (categories.getMCategory() != null) {
            this.mCategory = categories.getMCategory();
        }
        if (categories.getSCategory() != null) {
            this.sCategory = categories.getSCategory();
        }
    }
}
