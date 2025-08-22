package com.gulbi.Backend.domain.contract.contract.service;

import org.springframework.stereotype.Component;

import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.contract.contract.repository.ContractTemplateRepository;
import com.gulbi.Backend.domain.rental.product.entity.Product;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ContractTemplateRepoService {
	private final ContractTemplateRepository contractTemplateRepository;

	public ContractTemplate save(ContractTemplate template){
		return contractTemplateRepository.save(template);
	}

	public ContractTemplate findByProductIdWithProduct(Long productId){
		return contractTemplateRepository.findByProductIdWithProduct(productId).orElseThrow();
	}

	public ContractTemplate findById(Long templateId){
		return contractTemplateRepository.findById(templateId).orElseThrow();
	}

	public void deleteByProduct(Product product){
		contractTemplateRepository.deleteAllByProduct(product);
	}

}
