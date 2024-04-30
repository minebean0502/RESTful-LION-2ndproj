package com.hppystay.hotelreservation.payment.toss.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentCancelDto {
    private String cancelReason;
}
