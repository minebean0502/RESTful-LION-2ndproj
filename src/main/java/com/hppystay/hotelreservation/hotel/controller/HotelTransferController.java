package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.hotel.dto.AssignmentDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.service.HotelTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 1. 양도할 사람을 검색하는 API
    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String keyword) {
        List<MemberDto> members = hotelTransferService.searchByNicknameOrEmail(keyword);
        return ResponseEntity.ok(members);
    }

    // 3. assignment에서 필요한 정보들을 가져와야 하는 부분
    @GetMapping("/assignment")
    public ResponseEntity<AssignmentDto> getAssignment(@RequestParam Long reservationId) {
        AssignmentDto assignmentDto = hotelTransferService.readAssignment(reservationId);
        return ResponseEntity.ok(assignmentDto);
    }

    // 4. 양도 취소 - A로부터
    @PostMapping("/assignment/deny/A")
    public ResponseEntity<ReservationInfoDto> denyAssignment(
            @RequestParam Long reservationId) {
        ReservationInfoDto reservationInfoDto = hotelTransferService.ADenyAssignment(reservationId);
        return ResponseEntity.ok(reservationInfoDto);
    }

    // 5. 양도 거절 - B로부터
    @DeleteMapping("/assignment/deny/B")
    public ResponseEntity<ReservationInfoDto> deleteAssignment(
            @RequestParam Long reservationId
    ) {
        ReservationInfoDto reservationInfoDto = hotelTransferService.BDenyAssignment(reservationId);
        return ResponseEntity.ok(reservationInfoDto);
    }
}
