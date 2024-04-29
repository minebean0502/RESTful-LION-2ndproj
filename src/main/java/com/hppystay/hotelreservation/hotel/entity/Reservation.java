package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private LocalDate checkIn;
    @Setter
    private LocalDate checkOut;

    // 결제관련
    //    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //    private Payment payment;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Setter
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PAYMENT_PENDING;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToOne
    @Setter
    private Payment payment;
}
