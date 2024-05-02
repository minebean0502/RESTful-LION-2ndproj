package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE h.title LIKE CONCAT('%', :keyword, '%') OR h.area LIKE CONCAT('%', :keyword, '%')")
        // 와일드카드
    List<Hotel> searchByKeyword(String keyword);

    //특정 호텔에 대한 평균 별점과 리뷰 개수
    @Query("SELECT AVG(r.score), COUNT(r) FROM Review r WHERE r.hotel.id = :hotelId")
    List<Object[]> getHotelWithAll(@Param("hotelId") Long hotelId);

    @Query("SELECT DISTINCT h " +
            "FROM Hotel h " +
            "LEFT JOIN h.rooms r " +
            "LEFT JOIN r.reservationList res " +
            "WHERE (h.title LIKE CONCAT('%', :keyword, '%') OR h.area LIKE CONCAT('%', :keyword, '%')) " +
            "AND (res.checkOut <= :checkIn OR res.checkIn >= :checkOut OR res.id IS NULL OR res.status = :status) " +
            "ORDER BY " +
            "CASE WHEN :sort = 'title' THEN h.title END ASC, " +
            "CASE WHEN :sort = 'rating' THEN h.avg_score END DESC, " +
            "CASE WHEN :sort = 'reviewCount' THEN h.review_count END DESC")
    List<Hotel> searchByKeywordAndDateRangeAndSort(
            @Param("keyword") String keyword,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("sort") String sort,
            @Param("cancelStatus")ReservationStatus status
    );

    Optional<Hotel> findHotelByManager(Member manager);
}