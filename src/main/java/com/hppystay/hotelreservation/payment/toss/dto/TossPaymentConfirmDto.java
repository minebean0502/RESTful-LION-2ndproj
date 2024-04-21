package com.hppystay.hotelreservation.payment.toss.dto;

import lombok.Data;

@Data
public class TossPaymentConfirmDto {
    private String paymentKey;
    private String orderId;
    private String amount;
}
