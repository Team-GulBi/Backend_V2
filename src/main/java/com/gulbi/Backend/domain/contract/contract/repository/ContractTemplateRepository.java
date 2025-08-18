package com.gulbi.Backend.domain.contract.contract.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;

@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate,Long> {

	@Query("SELECT t FROM ContractTemplate t JOIN Product p ON p.contractTemplate = t WHERE p.id = :productId")
	Optional<ContractTemplate> findByProductId(@Param("productId") Long productId);
}
