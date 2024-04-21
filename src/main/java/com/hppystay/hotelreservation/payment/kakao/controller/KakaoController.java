package com.hppystay.hotelreservation.payment.kakao.controller;

import com.hppystay.hotelreservation.payment.kakao.dto.Request.KaKaoPaymentReadyDto;
import com.hppystay.hotelreservation.payment.kakao.service.KakaoHttpService;
import com.hppystay.hotelreservation.payment.kakao.service.KakaoService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService service;

    @PostMapping("/ready")
    public Object readyPayment(
            @RequestBody
            KaKaoPaymentReadyDto dto
    ) {
        log.info("카카오페이 시작했습니다");
        return service.readyPayment(dto);
    }
}