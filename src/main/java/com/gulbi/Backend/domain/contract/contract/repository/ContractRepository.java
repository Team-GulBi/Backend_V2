package com.gulbi.Backend.domain.contract.contract.repository;

import com.gulbi.Backend.domain.contract.contract.entity.Contract;
import com.gulbi.Backend.domain.contract.application.entity.Application;
import com.gulbi.Backend.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // 예약 아이디로 계약 조회
    Optional<Contract> findByApplicationId(Long applicationId);

}
