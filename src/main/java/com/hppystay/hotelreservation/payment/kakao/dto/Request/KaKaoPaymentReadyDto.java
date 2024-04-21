package com.hppystay.hotelreservation.payment.kakao.dto.Request;

import lombok.Data;

@Data
public class KaKaoPaymentReadyDto {
    // 결제 준비시 송신할 Dto

    private String cid;                 // 가맹점 코드 (테스트 가맹점 코드 넣을 곳)
    private String partner_order_id;    // 주문 번호
    private String partner_user_id;     // 회원 id
    private String item_name;           // 상품명
    private String quantity;            // 상품 수량
    private String total_amount;        // 상품 총액
    private String tax_free_amount;     // 상품 비과세 금액 (0이라 설정할것)
    private String approval_url;        // 결제 성공 redirect_url
    private String cancel_url;          // 결제 취소 redirect_url
    private String fail_url;            // 결제 실패 redirect_url
}
