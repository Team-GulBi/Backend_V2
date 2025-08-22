package com.gulbi.Backend.domain.contract.contract.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gulbi.Backend.domain.contract.contract.entity.ContractTemplate;
import com.gulbi.Backend.domain.rental.product.entity.Product;

import jakarta.transaction.Transactional;

@Repository
public interface ContractTemplateRepository extends JpaRepository<ContractTemplate,Long> {

	@Query("SELECT t FROM ContractTemplate t JOIN FETCH t.product WHERE t.product.id = :productId")
	Optional<ContractTemplate> findByProductIdWithProduct(@Param("productId") Long productId);

	@Transactional
	@Modifying
	void deleteAllByProduct(Product product);

}
