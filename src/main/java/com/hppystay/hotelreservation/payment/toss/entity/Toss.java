package com.hppystay.hotelreservation.payment.toss.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Toss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 여기에 링크지을 Reservation 건
    private String reservation;

    private String tossPaymentKey;
    private String tossOrderId;
    @Setter
    private String status;
}
