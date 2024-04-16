package com.hppystay.hotelreservation.payment.toss.temp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TempPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 중복 방지를 위함
    private String paymentUID;
    private String status;
    private String price;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private TempReservationEntity reservation;
    // reservation에서 가져올건
    // reservation의 id와, reservation에 연결된 member의 id
}
