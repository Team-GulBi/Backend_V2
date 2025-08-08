package com.gulbi.Backend.domain.contract.contract.repository;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.contract.contract.code.ContractErrorCode;
import com.gulbi.Backend.domain.contract.contract.entity.Contract;
import com.gulbi.Backend.domain.contract.contract.exception.ContractException;
import com.gulbi.Backend.global.error.DatabaseException;
import com.gulbi.Backend.global.error.ExceptionMetaDataFactory;

import jakarta.persistence.PersistenceException;

@Component
public class ContractRepoService {
	private final ContractRepository contractRepository;
	private final String className = this.getClass().getName();
	public ContractRepoService(ContractRepository contractRepository) {
		this.contractRepository = contractRepository;
	}
	public Contract findByApplicationId(Long applicationId){
		try {
			return contractRepository.findByApplicationId(applicationId).orElseThrow(
				() -> new ContractException(ExceptionMetaDataFactory.of(applicationId, className, null, ContractErrorCode.CONTRACT_NOT_FOUND))
			);//BusinessException
		} catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
				 PersistenceException exception) {
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(applicationId, className, exception, ContractErrorCode.CONTRACT_REPO_ERROR)
			);//InfrastructureException
		}
	}

	public Contract findById(Long contractId) {
		try {
			return contractRepository.findById(contractId)
				.orElseThrow(() -> new ContractException(
					ExceptionMetaDataFactory.of(contractId, className, null, ContractErrorCode.CONTRACT_NOT_FOUND)
				));//BusinessException
		}catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception){
				throw new DatabaseException(
					ExceptionMetaDataFactory.of(contractId, className,exception,ContractErrorCode.CONTRACT_REPO_ERROR)
				);//InfrastructureException
		}

	}

	public Contract save(Contract contract){
		try {
			return contractRepository.save(contract);
		}catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException | PersistenceException exception){
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(contract, className,exception,ContractErrorCode.CONTRACT_REPO_ERROR)
			);//InfrastructureException
		}

	}
	public void deleteById(Long contractId) {
		try {
			contractRepository.deleteById(contractId);
		} catch (EmptyResultDataAccessException | DataIntegrityViolationException | JpaSystemException |
				 PersistenceException exception) {
			throw new DatabaseException(
				ExceptionMetaDataFactory.of(contractId, className, exception, ContractErrorCode.CONTRACT_REPO_ERROR)
			);//InfrastructureException
		}
	}




}
