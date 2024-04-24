package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkIn;
    private LocalDate checkOut;

    // 결제관련
    //    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PAYMENT_PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
