package com.hppystay.hotelreservation.payment.toss.temp.repository;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempMemberRepository extends JpaRepository<TempMemberEntity, Long> {
}
