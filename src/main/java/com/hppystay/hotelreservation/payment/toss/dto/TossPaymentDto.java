package com.hppystay.hotelreservation.payment.toss.dto;

import com.hppystay.hotelreservation.payment.toss.entity.TossPayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentDto {
    private String status;
    private String category;

    private String tossPaymentKey;
    private String tossOrderId;
    private String totalAmount;

    private Long reservationId;

    private String requestedAt;
    private String approvedAt;
    private String lastTransactionKey;


    public static TossPaymentDto fromEntity(TossPayment entity) {
        return TossPaymentDto.builder()
                .status(entity.getStatus())
                .category("Toss")

                .tossPaymentKey(entity.getTossPaymentKey())
                .tossOrderId(entity.getTossOrderId())
                .totalAmount(entity.getTotalAmount())

                .reservationId(entity.getReservationId())

                .requestedAt(entity.getRequestedAt())
                .approvedAt(entity.getApprovedAt())
                .lastTransactionKey(entity.getLastTransactionKey())

                .build();
    }
}
