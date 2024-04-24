package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT h FROM Hotel h WHERE h.title LIKE CONCAT('%', :keyword, '%') OR h.area LIKE CONCAT('%', :keyword, '%')") // 와일드카드
    List<Hotel> searchByKeyword(String keyword);
}