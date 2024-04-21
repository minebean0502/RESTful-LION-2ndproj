package com.hppystay.hotelreservation.payment.kakao.repository;

import com.hppystay.hotelreservation.payment.kakao.entity.KakaoPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KaKaoRepository extends JpaRepository<KakaoPayment, Long> {
}
