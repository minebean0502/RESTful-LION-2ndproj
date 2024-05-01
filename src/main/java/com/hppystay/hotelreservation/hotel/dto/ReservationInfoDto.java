package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationInfoDto {
    private Long reservationId;
    private String hotelName;
    private String roomName;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String hotelImage;
    private ReservationStatus status;
    // roomId 필요해서 이부분 추가함
    private Long roomId;

    public static ReservationInfoDto fromEntity(Reservation entity) {
        return ReservationInfoDto.builder()
                .reservationId(entity.getId())
                .hotelName(entity.getRoom().getHotel().getTitle())
                .roomName(entity.getRoom().getName())
                .checkIn(entity.getCheckIn())
                .checkOut(entity.getCheckOut())
                .hotelImage(entity.getRoom().getHotel().getFirstImage())
                .status(entity.getStatus())
                // 이부분 추가함
                .roomId(entity.getRoom().getId())
                .build();
    }
}
