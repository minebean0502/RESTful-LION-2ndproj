package com.hppystay.hotelreservation.auth.repository;

import com.hppystay.hotelreservation.auth.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VerificationRepository extends JpaRepository<EmailVerification, Long> {
    boolean existsByEmail(String email);
    void deleteByEmail(String email);
}
