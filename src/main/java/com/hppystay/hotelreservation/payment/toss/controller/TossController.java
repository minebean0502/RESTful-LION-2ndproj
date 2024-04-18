package com.hppystay.hotelreservation.payment.toss.controller;

import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentCancelDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentDto;
import com.hppystay.hotelreservation.payment.toss.service.TossOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
    private final TossOrderService service;

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            TossPaymentConfirmDto dto
    ) {
        log.info("시작했습니다");
        log.info("received: {}", dto.toString());
        return service.confirmPayment(dto);
    }

    // 임시로 Reservation Controller 사용
    @GetMapping("/reservations")
    public List<TossPaymentDto> readAll() {
        return service.readAll();
    }

    @GetMapping("/reservation/{id}")
    public TossPaymentDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

    @GetMapping("/reservation/{id}/payment")
    public Object readTossPayment(
            @PathVariable("id")
            Long id
    ) {
        return service.readTossPayment(id);
    }

    @PostMapping("/reservation/{id}/cancel")
    public Object cancelPayment(
            @PathVariable("id")
            Long id,
            @RequestBody
            TossPaymentCancelDto dto
    ) {
        return service.cancelPayment(id, dto);
    }

}
