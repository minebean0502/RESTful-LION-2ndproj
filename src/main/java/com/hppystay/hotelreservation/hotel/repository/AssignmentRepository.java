package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Assignment;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // toMember(B)로 assignment의 정보를 찾는 부분
    Optional<Assignment> findByToMember(Member toMember);
    // 기존
    // Optional<Assignment> findByReservation(Reservation toReservation);
    Optional<Assignment> findByToReservation (Reservation toReservation);

    // A로부터 찾는법
    // Assignment findByFromMemberAndReservation(Member fromMember, Reservation fromReservation);
    Assignment findByFromMemberAndFromReservation(Member fromMember, Reservation fromReservation);

    // B로부터 찾는법
    // Assignment findByToMemberAndReservation(Member toMember, Reservation toReservationId);
    Assignment findByToMemberAndToReservation(Member toMember, Reservation toReservation);
}
