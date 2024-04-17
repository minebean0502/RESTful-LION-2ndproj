package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Reservation  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numberOfPeople;

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
