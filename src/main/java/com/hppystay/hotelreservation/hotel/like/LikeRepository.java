package com.hppystay.hotelreservation.hotel.like;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
   boolean existsByMemberAndHotel(Member member, Hotel hotel);
   void deleteByMemberAndHotel(Member member, Hotel hotel);
}
