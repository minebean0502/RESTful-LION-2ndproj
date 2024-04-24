package com.hppystay.hotelreservation.auth.repository;

import com.hppystay.hotelreservation.auth.entity.ManagerRequest;
import com.hppystay.hotelreservation.auth.entity.ManagerRequestStatus;
import com.hppystay.hotelreservation.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRequestRepository
        extends JpaRepository<ManagerRequest, Long> {
    Optional<ManagerRequest> findByMemberAndStatus(Member member, ManagerRequestStatus status);
}
