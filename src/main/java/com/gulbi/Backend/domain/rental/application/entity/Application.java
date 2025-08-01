package com.gulbi.Backend.domain.rental.application.entity;

import com.gulbi.Backend.domain.rental.product.entity.Product;
import com.gulbi.Backend.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.Period;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)  // 상품과 다대일 관계 설정
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)  // 사용자와 다대일 관계 설정
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;  // 상태 (reserving, using, rejected, returned)

    @Column(nullable = false)
    private LocalDate startDate;  // 시작 날짜

    @Column(nullable = false)
    private LocalDate endDate;  // 끝 날짜

    @Column(nullable = false)
    private int totalPrice;  // 총 가격

    // 시작 날짜와 끝 날짜의 차이를 계산하는 메서드
    private int calculateTotalDays() {
        if (startDate != null && endDate != null) {
            return Period.between(startDate, endDate).getDays() + 1;
        }
        return 1; // 만약 시작 또는 끝 날짜가 null인 경우 1을 반환
    }

    public Application(Product product, User user, LocalDate startDate, LocalDate endDate) {
        this.product = product;
        this.user = user;
        this.status = ApplicationStatus.RESERVING;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = calculateTotalDays() * product.getPrice();
    }

}
