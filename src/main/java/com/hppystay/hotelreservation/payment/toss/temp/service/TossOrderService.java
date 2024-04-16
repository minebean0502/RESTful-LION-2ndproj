package com.hppystay.hotelreservation.payment.toss.temp.service;

import com.hppystay.hotelreservation.payment.toss.dto.PaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.service.TossHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossOrderService {
    private final TossHttpService tossService;

    public Object confirmPayment(PaymentConfirmDto dto) {
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());
        return tossPaymentObj;
    }
}
