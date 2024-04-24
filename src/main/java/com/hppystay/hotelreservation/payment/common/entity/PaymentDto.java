package com.hppystay.hotelreservation.payment.common.entity;

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
    private String paymentKey;
    private String orderId;
    private String status;
    private String category;

//    public static PaymentDto fromEntity(Payment entity) {
//        return PaymentDto.builder()
//                .category(entity.getCategory())
//                .reservationId(entity.getReservation().getId())
//                .build();
//    }
}
