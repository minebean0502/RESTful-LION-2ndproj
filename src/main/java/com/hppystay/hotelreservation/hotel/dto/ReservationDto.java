package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class ReservationDto {

    private Long id;
    private Long roomId;
    private Long memberId;
    private LocalDate checkIn;
    private LocalDate checkOut;

    //TODO: 결제 관련 추가

    private ReservationStatus status;
}
