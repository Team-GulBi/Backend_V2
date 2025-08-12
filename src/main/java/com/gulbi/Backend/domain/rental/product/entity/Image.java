package com.gulbi.Backend.domain.rental.product.entity;

import com.gulbi.Backend.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Entity
@Table(name = "images")
@NoArgsConstructor
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 사용자와 다대일 관계 설정
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(length = 100000)
    private String url;

    @Column(nullable = false)
    private Boolean main;

    @Builder
    private Image(Product product, String url, Boolean main) {
        this.product = product;
        this.url = url;
        this.main = main;
    }

    public void updateMainStatus(boolean isMain) {
        this.main = isMain;
    }

}
