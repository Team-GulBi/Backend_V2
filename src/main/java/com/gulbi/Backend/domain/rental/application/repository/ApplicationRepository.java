package com.gulbi.Backend.domain.rental.application.repository;

import com.gulbi.Backend.domain.rental.application.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
