package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import com.hppystay.hotelreservation.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("SELECT DISTINCT r.room.id FROM Reservation r " +
            "WHERE r.checkIn < :checkOut AND r.checkOut > :checkIn " +
            "AND r.status <> 'RESERVATION_CANCELED'")
    List<Long> findUnavailableRoomIds(LocalDate checkIn, LocalDate checkOut);

    @Query("SELECT CASE WHEN (COUNT(r) > 0) THEN true ELSE false END " +
            "FROM Reservation r " +
            "WHERE r.room.id = :roomId " +
            "AND r.checkIn < :checkOut AND r.checkOut > :checkIn " +
            "AND r.status <> 'RESERVATION_CANCELED'")
    boolean existsConflictingReservation(Long roomId, LocalDate checkIn, LocalDate checkOut);

    List<Reservation> findAllByMember(Member member);

    // 멤버의 id와, status로 찾는 메서드
    List<Reservation> findByMemberIdAndStatus(Long memberId, ReservationStatus status);

    Reservation findByMember(Member member);

    Reservation findByMemberAndRoom(Member member, Room room);
}