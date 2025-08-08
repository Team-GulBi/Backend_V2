package com.gulbi.Backend.domain.contract.application.entity;

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

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "applications", uniqueConstraints = {
    @UniqueConstraint(name = "APPLICATION_START_END_DATE", columnNames={"startDate","endDate","product_id"})
})
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
    private LocalDateTime startDate;  // 시작 날짜

    @Column(nullable = false)
    private LocalDateTime endDate;  // 끝 날짜

    @Column(nullable = false)
    private int totalPrice;  // 총 가격



    public Application(Product product, User user, LocalDateTime startDate, LocalDateTime endDate) {
        this.product = product;
        this.user = user;
        this.status = ApplicationStatus.RESERVING;
        this.startDate = validate(startDate);
        this.endDate = validate(endDate);
        this.totalPrice = (int)(calculateTotalDays() * product.getPrice());
    }

    // 시작 날짜와 끝 날짜의 차이를 계산하는 메서드
    private long calculateTotalDays() {
        if (startDate != null && endDate != null) {
            // 시작과 끝 시간 사이의 총 시간(초 단위)을 시간 단위로 변환(소수점 버림)
            long hours = Duration.between(startDate, endDate).toHours();
            // 최소 1시간 이상은 계산되도록 보장
            return hours > 0 ? hours : 1;
        }
        return 1;
    }

    private LocalDateTime validate(LocalDateTime time) {
        if (time.getMinute() != 0 || time.getSecond() != 0 || time.getNano() != 0) {
            throw new IllegalArgumentException("시간의 분, 초, 나노초는 0이어야 합니다.");
        }
        return time;
    }

    public void markAsUsing(){
        this.status=ApplicationStatus.USING;
    }
    public void markAsRejected(){
        this.status=ApplicationStatus.REJECTED;
    }

}
