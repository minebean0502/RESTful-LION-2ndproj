package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
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


    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createTransferReservation(
            @RequestBody ReservationDto request,
            @RequestParam Long grantorReservationId
    ) {
        ReservationDto newTransferReservation = hotelTransferService.createTransferReservation(request, grantorReservationId);
        return new ResponseEntity<>(newTransferReservation, HttpStatus.CREATED);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<ReservationDto>> getPendingReservationsByMember() {
        List<ReservationDto> pendingReservations = hotelTransferService.getPendingReservationsByMember();
        return ResponseEntity.ok(pendingReservations);
    }

    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String keyword) {
        List<MemberDto> members = hotelTransferService.searchByNicknameOrEmail(keyword);
        return ResponseEntity.ok(members);
    }

}
