package com.hppystay.hotelreservation.payment.toss.controller;

import com.hppystay.hotelreservation.payment.toss.dto.PaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.temp.service.TossOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
    private final TossOrderService service;

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        log.info("시작했습니다");
        log.info("received: {}", dto.toString());
        return service.confirmPayment(dto);
    }
}
