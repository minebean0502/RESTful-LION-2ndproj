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
            @RequestParam("roomId")
            Long roomId,
            @RequestParam("reservationId")
            Long reservationId,
            @RequestBody
            TossPaymentConfirmDto dto
    ) {
        return service.confirmPayment(roomId, reservationId, dto);
    }

    // 양도받고, 양수자가 결제를 진행한 뒤, 양도자(A)의 환불을 진행하는 API
    // B의 reservationId를 가지고 뭔가 할 수 있나?
    @PostMapping("/payment/cancel-from-user")
    public Object assignConfirm(
            @RequestBody
            TossPaymentCancelDto dto) {
        return service.cancelPaymentBeforeUser(dto);
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
    @PostMapping("/reservation/{reservationId}/cancel")
    public Object cancelPayment(
            @PathVariable("reservationId")
            Long reservationId,
            @RequestBody
            TossPaymentCancelDto dto
    ) {
        return service.cancelPayment(reservationId, dto);
    }
}
