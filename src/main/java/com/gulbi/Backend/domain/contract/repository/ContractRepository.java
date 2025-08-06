package com.gulbi.Backend.domain.contract.repository;

import com.gulbi.Backend.domain.contract.entity.Contract;
import com.gulbi.Backend.domain.rental.application.entity.Application;
import com.gulbi.Backend.domain.user.entity.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
    // ToDo: DTO를 안쓰고 엔티티를 그대로 쓰는부분은 Crud클래스를 만들어서 변환 책임 위임 예정
    // 특정 대여인의 계약 목록 조회
    List<Contract> findByLender(User lender);

    // 특정 신청의 계약 목록 조회
    List<Contract> findByApplication(Application application);

    // 특정 차용인의 계약 목록 조회
    List<Contract> findByBorrower(User borrower);

    // 예약 아이디로 계약 조회
    Optional<Contract> findByApplicationId(Long applicationId);

}
