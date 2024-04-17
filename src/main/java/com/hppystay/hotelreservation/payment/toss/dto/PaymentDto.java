package com.hppystay.hotelreservation.payment.toss.dto;

import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    private Long id;
    // 어떤 방에 대한 예약인지
    private Long reservationId;
    private String tossPaymentKey;
    private String tossOrderId;
    private String status;

    public static PaymentDto fromEntity(Payment entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .reservationId(entity.getReservation().getId())
                .tossPaymentKey(entity.getTossPaymentKey())
                .tossOrderId(entity.getTossOrderId())
                .status(entity.getStatus())
                .build();
    }
}
