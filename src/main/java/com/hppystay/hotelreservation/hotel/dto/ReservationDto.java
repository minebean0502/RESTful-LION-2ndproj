package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
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

    public static ReservationDto fromEntity(Reservation entity) {
        return ReservationDto.builder()
                .id(entity.getId())
                .roomId(entity.getRoom().getId())
                .memberId(entity.getMember().getId())
                .checkIn(entity.getCheckIn())
                .checkOut(entity.getCheckOut())
                .status(entity.getStatus())
                .build();
    }
}
