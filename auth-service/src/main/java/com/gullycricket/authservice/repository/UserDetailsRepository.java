package com.gullycricket.authservice.repository;

import com.gullycricket.authservice.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDetailsRepository extends JpaRepository <UserDetails, Long> {
    List<UserDetails> findTop10ByLegIdOrderByPointsDesc(Long legId);
    List<UserDetails> findAllByLegId(Long legId);
    Optional<UserDetails> findByUserId(Long userId);
}