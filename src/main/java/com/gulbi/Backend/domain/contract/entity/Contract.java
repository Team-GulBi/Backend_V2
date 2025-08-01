package com.gulbi.Backend.domain.contract.entity;

import com.gulbi.Backend.domain.rental.application.entity.Application;
import com.gulbi.Backend.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contracts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    // 대여인 (User와 ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_id", nullable = false)
    private User lender;

    // 차용인 (User와 ManyToOne 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id", nullable = false)
    private User borrower;

    // 물품명
    @Column(nullable = false)
    private String itemName;

    // 형식 및 규격
    private String specifications;

    // 수량
    @Column(nullable = false)
    private Integer quantity;

    // 상태
    @Column(name = "`condition`") // 예약어 충돌 감지 ` 삽입
    private String condition;

    // 비고
    private String notes;

    // 대여 종료일
    private LocalDateTime rentalEndDate;

    // 대여 장소
    private String rentalPlace;

    // 대여 상세 주소
    private String rentalDetailAddress;

    // 반납 날짜
    private LocalDateTime returnDate;

    // 반납 장소
    private String returnPlace;

    // 반납 상세 주소
    private String returnDetailAddress;

    // 대여 요금
    @Column(precision = 10, scale = 2)
    private BigDecimal rentalFee;

    // 결제일
    private LocalDateTime paymentDate;

    // 연체 이자율
    @Column(precision = 5, scale = 2)
    private BigDecimal lateInterestRate;

    // 연체 벌금율
    @Column(precision = 5, scale = 2)
    private BigDecimal latePenaltyRate;

    // 손상 보상율
    @Column(precision = 5, scale = 2)
    private BigDecimal damageCompensationRate;

    // 계약 생성일
    @Column(nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();

    // 대여인 승인 여부 (기본값 false)
    @Column(nullable = false)
    @Builder.Default
    private Boolean lenderApproval = false;

    // 차용인 승인 여부 (기본값 false)
    @Column(nullable = false)
    @Builder.Default
    private Boolean borrowerApproval = false;

    // 계약 관련 URL (기본값 null)
    @Column(length = 10000)
    private String url;

    public void approveByLender() {
        this.lenderApproval = true;
    }

    public void approveByBorrower() {
        this.borrowerApproval = true;
    }

    public void updateUrl(String url) {
        this.url = url;
    }

}
