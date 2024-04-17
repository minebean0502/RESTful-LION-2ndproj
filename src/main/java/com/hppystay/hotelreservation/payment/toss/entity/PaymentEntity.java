package com.hppystay.hotelreservation.payment.toss.entity;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 여기 아래부터는 필수 요소
    // 중복 방지를 위함
    private String tossPaymentKey;
    private String tossOrderId;
    @Setter
    private String status;
    // 여기 위에는 필수 요소

    // Reservation의 price 가져올 것?
    private String price;

    @OneToOne
    @JoinColumn(name = "reservation_id")
    private TempReservationEntity reservation;
    // reservation에서 가져올건
    // reservation의 id와, reservation에 연결된 member의 id
}