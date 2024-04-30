package com.hppystay.hotelreservation.payment.toss.repository;

import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
