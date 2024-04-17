package com.hppystay.hotelreservation.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {


    RESERVATION_AVAILABLE("예약가능"),
    RESERVATION_COMPLETED("예약완료"),
    RESERVATION_CANCEL("예약취소"),
    PAYMENT_PENDING("결제대기");

    private final String label;
}
