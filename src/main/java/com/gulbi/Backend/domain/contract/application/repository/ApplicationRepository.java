package com.gulbi.Backend.domain.contract.application.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusDetailResponse;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusProjection;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.application.entity.ApplicationStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
	@Query(value = """
		SELECT 
        DATE(a.start_date) AS reservationDate,
        MAX(CASE WHEN a.status = 'RESERVING' THEN TRUE ELSE FALSE END) AS hasReserving,
        MAX(CASE WHEN a.status IN ('RESERVING', 'USING') THEN TRUE ELSE FALSE END) AS hasReservingOrUsing
    FROM 
        applications a
    WHERE 
        a.product_id = :productId
        AND a.start_date BETWEEN :startOfMonth AND :endOfMonth
    GROUP BY 
        DATE(a.start_date)
    """, nativeQuery = true)
	List<ApplicationStatusProjection> findReservationStatusByMonth(
		@Param("productId") Long productId,
		// 입력받은 월의 시작 시각 (예: 2025-08-01T00:00:00)
		@Param("startOfMonth") LocalDateTime startOfMonth,
		// 입력받은 월의 마지막 시각 (예: 2025-08-31T23:59:59)
		@Param("endOfMonth") LocalDateTime endOfMonth
	);


	@Query("""
    SELECT new com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusDetailResponse(
        a.startDate, a.endDate, a.status, a.id
    )
    FROM Application a
    WHERE a.product.id = :productId
    AND FUNCTION('DATE', a.startDate) = :date
""")
	List<ApplicationStatusDetailResponse> findByProductIdAndDate(
		@Param("productId") Long productId,
		@Param("date") LocalDate date
	);

	@Transactional
	@Modifying
	@Query(value = """
	UPDATE Application a
	SET a.status = :status
	WHERE a.id = :applicationId
""")
	void updateApplcationStatus(@Param("applicationId") Long applicationId, @Param("status")ApplicationStatus status);





}