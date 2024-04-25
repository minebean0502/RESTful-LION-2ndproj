package com.hppystay.hotelreservation.hotel.temp;

import com.hppystay.hotelreservation.auth.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempRepository extends JpaRepository<Member, Long> {
    List<Member> findByEmailContainingOrNicknameContaining(String email, String nickname);
}
