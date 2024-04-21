package com.hppystay.hotelreservation.payment.kakao.dto;

import lombok.Data;

@Data
public class KakaoPaymentDeclineDto {
    private String cid;
    private String tid;
    private String cancel_amount;
    private String cancel_tax_free_amount;

    // payload에 대해서는 조금 모르겠어서 고민중
}
