package com.gulbi.Backend.domain.contract.contract.repository;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.contract.contract.entity.Contract;

@Component
public class ContractRepoService {
	private final ContractRepository contractRepository;

	public ContractRepoService(ContractRepository contractRepository) {
		this.contractRepository = contractRepository;
	}

	public Contract findById(Long contractId){
		return contractRepository.findById(contractId).orElseThrow();
	}
	public Contract save(Contract contract){
		return contractRepository.save(contract);
	}
	public void deleteById(Long contractId){
		contractRepository.deleteById(contractId);
	}
	public Contract findByApplicationId(Long applicationId){
		return contractRepository.findByApplicationId(applicationId).orElseThrow();
	}

}
