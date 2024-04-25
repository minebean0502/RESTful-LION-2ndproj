package com.hppystay.hotelreservation.hotel.temp;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempReservRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status);
    List<Reservation> findByMemberId(Long memberId);
}
