package com.hppystay.hotelreservation.payment.toss.temp.service;

import com.hppystay.hotelreservation.payment.toss.temp.dto.TempReservationDto;

import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
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

    // 테스트 데이터이므로 임시로 전부 생성
    /*
    public TempReservationService(TempReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
        if (this.reservationRepository.count() == 0 ) {
            this.reservationRepository.saveAll(List.of(
                    TempReservationEntity.builder()
                            .memberEmail("user1@gmail.com")
                            .roomId("500")
                            .numberOfPeople("5")
                            .price("50000")
                            .status("IN_PROGRESS")
                            // 나중에 지울곳
                            .imageUrl("/static/tossImage/hotel1.png")
                            .build(),
                    TempReservationEntity.builder()
                            .memberEmail("user2@gmail.com")
                            .roomId("1")
                            .numberOfPeople("1")
                            .price("10000")
                            .status("IN_PROGRESS")
                            // 나중에 지울곳
                            .imageUrl("/static/tossImage/hotel2.png")
                            .build(),
                    TempReservationEntity.builder()
                            .memberEmail("user3@gmail.com")
                            .roomId("2")
                            .numberOfPeople("10")
                            .price("12345")
                            .status("IN_PROGRESS")
                            // 나중에 지울곳
                            .imageUrl("/static/tossImage/hotel3.png")
                            .build(),
                    TempReservationEntity.builder()
                            .memberEmail("user4@gmail.com")
                            .roomId("10")
                            .numberOfPeople("1")
                            .price("20000")
                            .status("IN_PROGRESS")
                            // 나중에 지울곳
                            .imageUrl("/static/tossImage/hotel4.png")
                            .build()
            ));
        }
    }
     */

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
