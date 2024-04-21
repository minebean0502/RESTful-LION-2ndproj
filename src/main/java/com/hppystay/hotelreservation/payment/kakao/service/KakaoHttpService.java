package com.hppystay.hotelreservation.payment.kakao.service;

import com.hppystay.hotelreservation.payment.kakao.dto.KaKaoPaymentConfirmDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/payment")
public interface KakaoHttpService {
    @PostExchange("/ready")
    Object confirmPayment(@RequestBody KaKaoPaymentConfirmDto dto);
}
