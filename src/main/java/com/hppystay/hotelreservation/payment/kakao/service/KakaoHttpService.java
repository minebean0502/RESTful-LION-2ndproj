package com.hppystay.hotelreservation.payment.kakao.service;

import com.hppystay.hotelreservation.payment.kakao.dto.Request.KaKaoPaymentReadyDto;
import com.hppystay.hotelreservation.payment.kakao.dto.Request.KakaoPaymentConfirmDto;
import com.hppystay.hotelreservation.payment.kakao.dto.Request.KakaoPaymentDeclineDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/payment")
public interface KakaoHttpService {
    @PostExchange("/ready")
    Object readyPayment(@RequestBody KaKaoPaymentReadyDto dto);

    @PostExchange("/approve")
    Object confirmPayment(@RequestBody KakaoPaymentConfirmDto dto);

    @GetExchange("/order")
    Object getPayment();

    @PostExchange("/cancel")
    Object cancelPayment(@RequestBody KakaoPaymentDeclineDto dto);
}
