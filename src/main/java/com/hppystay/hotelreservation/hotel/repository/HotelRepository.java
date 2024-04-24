package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    //특정 호텔에 대한 평균 별점과 리뷰 개수
    @Query("SELECT AVG(r.score), COUNT(r) FROM Review r WHERE r.hotel.id = :hotelId")
    List<Object[]> getHotelWithAll(@Param("hotelId") Long hotelId);
}

