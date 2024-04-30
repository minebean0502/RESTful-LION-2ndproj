package com.hppystay.hotelreservation.hotel.repository;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.HotelLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelLikeRepository extends JpaRepository<HotelLike, Long> {
   boolean existsByMemberAndHotel(Member member, Hotel hotel);
   void deleteByMemberAndHotel(Member member, Hotel hotel);
}
