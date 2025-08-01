package com.gulbi.Backend.domain.contract.repository;

import com.gulbi.Backend.domain.contract.entity.Contract;
import com.gulbi.Backend.domain.rental.application.entity.Application;
import com.gulbi.Backend.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {

    // 특정 대여인의 계약 목록 조회
    List<Contract> findByLender(User lender);

    // 특정 신청의 계약 목록 조회
    List<Contract> findByApplication(Application application);

    // 특정 차용인의 계약 목록 조회
    List<Contract> findByBorrower(User borrower);
}
