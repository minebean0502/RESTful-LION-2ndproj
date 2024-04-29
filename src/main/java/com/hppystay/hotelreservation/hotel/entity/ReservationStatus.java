package com.hppystay.hotelreservation.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ReservationStatus {

    PAYMENT_PENDING("결제대기"),
    RESERVATION_COMPLETED("결제완료"),
    ASSIGNMENT_PENDING("양도대기"),
    ASSIGNMENT_PAYMENT_PENDING("양도결제대기"),
    ASSIGNMENT_COMPLETED("양도완료"),
    RESERVATION_CANCELED("예약취소");

    private final String label;
}