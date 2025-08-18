package com.gulbi.Backend.domain.contract.contract.entity;

import java.math.BigDecimal;

import com.gulbi.Backend.domain.contract.contract.dto.TemplateUpdateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contract_templates")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String specification;
	@Column(name = "`condition`")
	private String condition;
	private String note;
	private String rentalPlace;
	private String returnPlace;
	// 연체 이자율
	@Column(precision = 5, scale = 2)
	private BigDecimal lateInterestRate;
	// 연체 벌금율
	@Column(precision = 5, scale = 2)
	private BigDecimal latePenaltyRate;
	// 손상 보상율
	@Column(precision = 5, scale = 2)
	private BigDecimal damageCompensationRate;

	public void update(TemplateUpdateRequest request){
		this.specification = request.getSpecification() != null ? request.getSpecification() : this.specification;
		this.condition = request.getCondition() != null ? request.getCondition() : this.condition;
		this.note = request.getNote() != null ? request.getNote() : this.note;
		this.rentalPlace = request.getRentalPlace() != null ? request.getRentalPlace() : this.rentalPlace;
		this.returnPlace = request.getReturnPlace() != null ? request.getReturnPlace() : this.returnPlace;
		this.lateInterestRate = request.getLateInterestRate() != null ? request.getLateInterestRate() : this.lateInterestRate;
		this.latePenaltyRate = request.getLatePenaltyRate() != null ? request.getLatePenaltyRate() : this.latePenaltyRate;
		this.damageCompensationRate = request.getDamageCompensationRate() != null ? request.getDamageCompensationRate() : this.damageCompensationRate;
	}


}

