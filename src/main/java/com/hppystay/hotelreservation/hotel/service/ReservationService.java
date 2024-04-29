package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepo;
    private final RoomRepository roomRepo;
    private final AuthenticationFacade facade;


    public ReservationDto createReservation(ReservationDto reservationDto) {
        //TODO: 예약 가능 여부 체크하기

        Member member = facade.getCurrentMember();

        Room room = roomRepo.findById(reservationDto.getRoomId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ReservationDto.fromEntity(reservationRepo.save(
                Reservation.builder()
                        .room(room)
                        .member(member)
                        .checkIn(reservationDto.getCheckIn())
                        .checkOut(reservationDto.getCheckOut())
                        .status(ReservationStatus.PAYMENT_PENDING) // 예약 생성 시 결제대기
                        .build()
        ));
    }

    public List<ReservationInfoDto> readAllMyReservation() {
        Member member = facade.getCurrentMember();
        // TODO 이 부분에 대해 status가 완료된 예약만 조회하게 바꿀수도 있음
        return reservationRepo.findAllByMember(member).stream()
                .map(ReservationInfoDto::fromEntity)
                .toList();
    }

    public List<ReservationInfoDto> readAllPendingReservation() {
        Member member = facade.getCurrentMember();
        return reservationRepo.findByMemberIdAndStatus(
                member.getId(), ReservationStatus.ASSIGNMENT_PAYMENT_PENDING).stream()
                .map(ReservationInfoDto::fromEntity)
                .toList();
    }
}
