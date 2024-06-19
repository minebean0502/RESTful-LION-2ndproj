package com.hppystay.hotelreservation.payment.toss.temp.controller;


import com.hppystay.hotelreservation.payment.toss.temp.dto.TempReservationDto;
import com.hppystay.hotelreservation.payment.toss.temp.service.TempReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Temp Controller", description = "메인 로직 완성전 결제를 위해 만든 Test 환경")
@Slf4j
@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class TempReservationController {
    private final TempReservationService reservationService;

    @GetMapping("/lists")
    public List<TempReservationDto> readAll() {
        log.info(String.valueOf(reservationService.readAll()));
        return reservationService.readAll();
    }

    @GetMapping("{id}")
    public TempReservationDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return reservationService.readOne(id);
    }
}