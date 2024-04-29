package com.hppystay.hotelreservation.payment.toss.temp.dto;

import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentDto;
import com.hppystay.hotelreservation.payment.toss.entity.TossPayment;
import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TempReservationDto {
    private Long id;
    private String memberEmail;
    private String roomId;
    private String numberOfPeople;
    private String price;
    private String status;
    private String imageUrl;
    private TossPaymentDto payment;

    public static TempReservationDto fromEntity(TempReservationEntity reservation){
        return TempReservationDto.builder()
                .id(reservation.getId())
                .memberEmail(reservation.getMemberEmail())
                .roomId(reservation.getRoomId())
                .numberOfPeople(reservation.getNumberOfPeople())
                .price(reservation.getPrice())
                .status(reservation.getStatus())

                // 나중에 지울곳
                .imageUrl(reservation.getImageUrl())
                .build();
    }
}
