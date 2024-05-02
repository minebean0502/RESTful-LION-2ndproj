package com.hppystay.hotelreservation.payment.toss.entity;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TossPayment extends Payment{
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String status;
    @Setter
    private String category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private TempReservationEntity reservation;
    를 상속받은 상태
     */

    private String tossPaymentKey;          // 결제/조회를 위한 Key (토스측)
    private String tossOrderId;             // 아이템의 주문 ID (관리자측)
    private String totalAmount;             // 총 결제 금액

    private Long reservationId;             // 몇번 예약의 ID인지

    private String requestedAt;
    private String approvedAt;
    private String lastTransactionKey;
}