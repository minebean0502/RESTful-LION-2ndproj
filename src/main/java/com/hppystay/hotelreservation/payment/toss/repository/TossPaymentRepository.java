package com.hppystay.hotelreservation.payment.toss.repository;

import com.hppystay.hotelreservation.payment.toss.entity.TossPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TossPaymentRepository extends JpaRepository<TossPayment, Long> {
}
