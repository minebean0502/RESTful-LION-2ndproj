package com.hppystay.hotelreservation.payment.kakao.controller;

import com.hppystay.hotelreservation.payment.kakao.service.KakaoHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoHttpService service;
}
