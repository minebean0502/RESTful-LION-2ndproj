package com.hppystay.hotelreservation.payment.kakao.service;

import com.hppystay.hotelreservation.payment.kakao.repository.KaKaoRepository;
import com.hppystay.hotelreservation.payment.toss.temp.repository.TempReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoHttpService kakaoService;
    private final KaKaoRepository kaKaoRepository;
    private final TempReservationRepository reservationRepository;
}
