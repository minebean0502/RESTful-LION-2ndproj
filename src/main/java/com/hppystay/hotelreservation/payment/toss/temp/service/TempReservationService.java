package com.hppystay.hotelreservation.payment.toss.temp.service;

import com.hppystay.hotelreservation.payment.toss.temp.dto.TempReservationDto;

import com.hppystay.hotelreservation.payment.toss.temp.repository.TempReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TempReservationService {
    private final TempReservationRepository reservationRepository;

    // 전부 조회
    public List<TempReservationDto> readAll() {
        return reservationRepository.findAll().stream()
                .map(TempReservationDto::fromEntity)
                .toList();
    }

    // 하나만 조회
    public TempReservationDto readOne(Long id) {
        return reservationRepository.findById(id)
                .map(TempReservationDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
