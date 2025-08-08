package com.gulbi.Backend.domain.contract.application.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import com.gulbi.Backend.domain.contract.application.code.ApplicationErrorCode;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusDetailResponse;
import com.gulbi.Backend.domain.contract.application.dto.ApplicationStatusProjection;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.contract.application.exception.ApplicationException;
import com.gulbi.Backend.global.error.DatabaseException;
import com.gulbi.Backend.global.error.ExceptionMetaData;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;
import com.gulbi.Backend.global.error.InfraErrorCode;
import jakarta.persistence.PersistenceException;

@Component
public class ApplicationRepoService {
	private final ApplicationRepository applicationRepository;
	private final String className=this.getClass().getName();

	public ApplicationRepoService(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

	public Application save(Application application) {
		try {
			return applicationRepository.save(application);
		}catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
				PersistenceException exception) {
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(application, className, exception, InfraErrorCode.DB_EXCEPTION)
			);//InfrastructureException
		}

	}

	public List<ApplicationStatusProjection> findReservationStatusByMonth(Long productId, LocalDateTime startOfMonth,
		LocalDateTime endOfMonth) {

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.put("productId", productId);
		argsMap.put("startOfMonth", startOfMonth);
		argsMap.put("endOfMonth", endOfMonth);

		try {
			List<ApplicationStatusProjection> list = applicationRepository.findReservationStatusByMonth(productId,
				startOfMonth, endOfMonth);
			if (list.isEmpty()) {
				ExceptionMetaData metaData = ExceptionMetaDataFactory.of(argsMap, className, null,
					ApplicationErrorCode.APPLICATION_NOT_FOUND);
				throw new ApplicationException(metaData);
			}
			return list;
		} catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
				 PersistenceException exception) {
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(argsMap, className, exception, InfraErrorCode.DB_EXCEPTION)
			);//InfrastructureException
		}
	}

	public List<ApplicationStatusDetailResponse> findByProductIdAndDate(Long productId, LocalDate date) {

		Map<String, Object> argsMap = new HashMap<>();
		argsMap.put("productId", productId);
		argsMap.put("yearMonth", date);

		try {
			List<ApplicationStatusDetailResponse> list = applicationRepository.findByProductIdAndDate(productId, date);
			if (list.isEmpty()) {
				throw new ApplicationException(
					ExceptionMetaDataFactory.of(argsMap, className, null, InfraErrorCode.DB_EXCEPTION));
			}
			return list;
		} catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
			   PersistenceException exception) {
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(argsMap, className, exception, InfraErrorCode.DB_EXCEPTION)
			);//InfrastructureException
		}
	}


}