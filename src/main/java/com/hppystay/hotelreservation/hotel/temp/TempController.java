package com.hppystay.hotelreservation.hotel.temp;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.payment.toss.temp.service.TempReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mypage")
@RequiredArgsConstructor
public class TempController {
    private final TempService tempService;

    @GetMapping("/member/search")
    public ResponseEntity<List<MemberDto>> searchMembers(@RequestParam String keyword) {
        List<MemberDto> members = tempService.searchByNicknameOrEmail(keyword);
        return ResponseEntity.ok(members);
    }

    @GetMapping("/myReservations")
    public ResponseEntity<List<ReservationDto>> getMyReservations(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        //Long memberId = userDetails.getMember().getId();
        Long memberId = 7L;
        List<ReservationDto> myReservations = tempService.getReservationsByMember(memberId);
        return ResponseEntity.ok(myReservations);
    }



//    @PostMapping("/reservation/create")
//    public ResponseEntity<TempDto> createReservation(@RequestBody TempDto request) {
//        TempDto newReservation = tempService.createReservation(request);
//        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
//    }

//    @GetMapping("/pending")
//    public ResponseEntity<List<TempDto>> getPendingReservationsByMember(
//            @AuthenticationPrincipal CustomUserDetails userDetails) {
//        //Long memberId = userDetails.getMember().getId(); // Or any other logic to determine memberId
//        Long memberId = 3L;
//        List<TempDto> pendingReservations = tempService.getPendingReservationsByMember(memberId);
//        return ResponseEntity.ok(pendingReservations);
//    }

//    /**
//     * 마이페이지에서 '양도받기'버튼을 눌렀을 때,
//     * 양도 승인 페이지로 이동합니다.
//     * @return accept-transfer.html 페이지를 반환합니다.
//     */
//    @PostMapping("/hotel/reservation/transfer/accept")
//    public ResponseEntity<TempDto> acceptTransfer(@RequestBody Long reservationId) {
//        try{
//            TempDto reservationDetails = tempService.getReservationDetails(reservationId);
//            return ResponseEntity.ok(reservationDetails);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//    }

}
