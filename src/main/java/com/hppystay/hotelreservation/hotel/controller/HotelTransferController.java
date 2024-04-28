package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.service.HotelTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotel/reservation/transfer")
@RequiredArgsConstructor
public class HotelTransferController {

    private final HotelTransferService hotelTransferService;

    // 2. 양도할 사람을 검색 완료해서, 양수자의 예약을 생성하고 Assignment에 저장하는 API
    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createTransferReservation(
            @RequestBody ReservationDto request,
            @RequestParam Long grantorReservationId
    ) {
        ReservationDto newTransferReservation = hotelTransferService.createTransferReservation(request, grantorReservationId);
        return new ResponseEntity<>(newTransferReservation, HttpStatus.CREATED);
    }

    // 3. 양수자가 받은 Reservation을 찾고, 양도에 관련된 로직을 진행하는 부분
    @GetMapping("/pending")
    public List<ReservationInfoDto> getPendingReservationsByMember() {
        log.info("컨트롤러: "+ hotelTransferService.getPendingReservationsByMember());
        return hotelTransferService.getPendingReservationsByMember();
    }

    // 1. 양도할 사람을 검색하는 API
    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String keyword) {
        List<MemberDto> members = hotelTransferService.searchByNicknameOrEmail(keyword);
        return ResponseEntity.ok(members);
    }

}
