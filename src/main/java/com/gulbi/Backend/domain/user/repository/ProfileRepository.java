package com.gulbi.Backend.domain.user.repository;

import com.gulbi.Backend.domain.user.entity.Profile;
import com.gulbi.Backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    Optional<Profile> findByUserId(Long id);
}