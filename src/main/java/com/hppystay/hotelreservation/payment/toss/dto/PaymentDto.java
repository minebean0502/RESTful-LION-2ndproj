package com.hppystay.hotelreservation.payment.toss.dto;

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
    // 어떤 예약 ID인지
    private Long reservationId;
    // 어떤 사람의 예약인지 (ID)
    private Long memberId;
    private String tossPaymentKey;
    private String tossOrderId;
    private String status;
    private String price;

    public static PaymentDto fromEntity(PaymentDto entity) {
        return PaymentDto.builder()
                .id(entity.getId())
                .reservationId(entity.getReservationId())
                .memberId(entity.memberId)
                .tossPaymentKey(entity.tossPaymentKey)
                .tossOrderId(entity.tossOrderId)
                .status(entity.getStatus())
                .price(entity.getPrice())
                .build();
    }
}
