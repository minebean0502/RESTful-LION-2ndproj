package com.hppystay.hotelreservation.payment.toss.temp.repository;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TempPaymentRepository extends JpaRepository<TempPaymentEntity, Long> {
}
