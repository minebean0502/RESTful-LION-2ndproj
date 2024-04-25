package com.hppystay.hotelreservation.payment.toss.controller;

import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentCancelDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentDto;
import com.hppystay.hotelreservation.payment.toss.service.TossService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/toss")
@RequiredArgsConstructor
public class TossController {
    private final TossService service;

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

    // 해당 Reservation에 대한 결제정보(TossPayment의 내용)
    @GetMapping("/reservation/{id}")
    public TossPaymentDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

    // 해당 ReservationId로 본 payment의 전체 정보
    @GetMapping("/reservation/{id}/payment")
    public Object readTossPayment(
            @PathVariable("id")
            Long id
    ) {
        return service.readTossPayment(id);
    }

    // 해당 Reservation의 결제 취소
    @PostMapping("/reservation/{id}/cancel")
    public Object cancelPayment(
            @PathVariable("id")
            Long id,
            @RequestBody
            TossPaymentCancelDto dto
    ) {
        return service.cancelPayment(id, dto);
    }

    // TODO A가 B에게 양도를 시작합니다
    // B가 양도 승인 눌렀을 때
    // B는 결제를 시작합니다
    // 결제에 받을 정보는

//    @PostMapping("/reservation/assign/confirm")
//    public Object assignConfirm() {
//        return
//    }
}
