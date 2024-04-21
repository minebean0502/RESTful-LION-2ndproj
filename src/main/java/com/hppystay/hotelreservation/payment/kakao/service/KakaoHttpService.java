package com.hppystay.hotelreservation.payment.kakao.service;

import com.hppystay.hotelreservation.payment.kakao.dto.KaKaoPaymentReadyDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/payments")
public interface KakaoHttpService {
    @PostExchange("/ready")
    Object confirmPayment(@RequestBody KaKaoPaymentReadyDto dto);
}
