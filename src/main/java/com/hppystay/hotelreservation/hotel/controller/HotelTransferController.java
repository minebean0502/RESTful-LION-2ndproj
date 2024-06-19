package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.hotel.dto.AssignmentDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.service.HotelTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "Hotel Transfer Controller", description = "예약 양도 API")
@RestController
@RequestMapping("/api/hotel/reservation/transfer")
@RequiredArgsConstructor
public class HotelTransferController {

    private final HotelTransferService hotelTransferService;

    // 2. 양도할 사람을 검색 완료해서, 양수자의 예약을 생성하고 Assignment에 저장하는 API
    @Operation(summary = "양도 절차 시작", description = "A의 예약 정보 변경, B의 예약 정보 생성, 양도할 데이터 생성")
    @PostMapping("/create")
    public ResponseEntity<ReservationDto> createTransferReservation(
            @RequestBody ReservationDto request,
            @RequestParam Long grantorReservationId
    ) {
        ReservationDto newTransferReservation = hotelTransferService.createTransferReservation(request, grantorReservationId);
        return new ResponseEntity<>(newTransferReservation, HttpStatus.CREATED);
    }

    // 1. 양도할 사람을 검색하는 API
    @Operation(summary = "양도할 사람 검색", description = "키워드는 유저명 / 유저 이메일입니다")
    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String keyword) {
        List<MemberDto> members = hotelTransferService.searchByNicknameOrEmail(keyword);
        return ResponseEntity.ok(members);
    }

    // 3. assignment에서 필요한 정보들을 가져와야 하는 부분
    @Operation(summary = "양도 데이터 요청", description = "이전 사용자로부터 양도에 필요한 데이터를 받아옴")
    @GetMapping("/assignment")
    public ResponseEntity<AssignmentDto> getAssignment(@RequestParam Long reservationId) {
        AssignmentDto assignmentDto = hotelTransferService.readAssignment(reservationId);
        return ResponseEntity.ok(assignmentDto);
    }

    // 4. 양도 취소 - A로부터
    @Operation(summary = "양도 취소", description = "양도자(A)가 양수자(B)의 양도 완료 전에 취소 할 경우 요청")
    @PostMapping("/assignment/deny/A")
    public ResponseEntity<ReservationInfoDto> denyAssignment(
            @RequestParam Long reservationId) {
        ReservationInfoDto reservationInfoDto = hotelTransferService.ADenyAssignment(reservationId);
        return ResponseEntity.ok(reservationInfoDto);
    }

    // 5. 양도 거절 - B로부터
    @Operation(summary = "양도 거절", description = "양수자(B)가 양도를 거절할 경우 요청")
    @DeleteMapping("/assignment/deny/B")
    public ResponseEntity<ReservationInfoDto> deleteAssignment(
            @RequestParam Long reservationId
    ) {
        ReservationInfoDto reservationInfoDto = hotelTransferService.BDenyAssignment(reservationId);
        return ResponseEntity.ok(reservationInfoDto);
    }
}
