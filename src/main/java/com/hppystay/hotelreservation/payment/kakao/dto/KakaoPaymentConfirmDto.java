package com.hppystay.hotelreservation.payment.kakao.dto;

import lombok.Data;

@Data
public class KakaoPaymentConfirmDto {
    private String cid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
    private String total_amount;

    // payload에 대해서는 조금 모르겠어서 고민중
}
