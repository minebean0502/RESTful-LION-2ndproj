package com.hppystay.hotelreservation.hotel.temp;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TempService {
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
    private final TempRepository tempRepository;
    private final ReservationRepository reservationRepository;
    private final TempReservRepository tempReservRepository;

    public List<MemberDto> searchByNicknameOrEmail(String keyword) {
        List<Member> members = tempRepository.findByEmailContainingOrNicknameContaining(keyword, keyword);
        return members.stream().map(MemberDto::fromEntity).collect(Collectors.toList());
    }

    public List<ReservationDto> getReservationsByMember(Long memberId) {
        List<Reservation> reservations = tempReservRepository.findByMemberId(memberId);
        return reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

//    public TempDto createReservation(TempDto request) {
//
//        Member member = memberRepository.findById(request.getMemberId())
//                .orElseThrow(() -> new RuntimeException("Member not found")); // 멤버 조회
//
//
//        Room room = roomRepository.findById(1L)
//                .orElseThrow(() -> new RuntimeException("Room not found"));
//
//        Reservation reservation = new Reservation();
//        reservation.setMember(member); // 조회한 멤버 설정
//        reservation.setStatus(ReservationStatus.PAYMENT_PENDING); // 상태 설정
//        //reservation.setNumberOfPeople(2); // 임시 데이터
//        reservation.setRoom(room); // 임시 방 객체
//
//        reservationRepository.save(reservation);
//
//        return TempDto.fromEntity(reservation);
//    }
//
//    public List<TempDto> getPendingReservationsByMember(Long memberId) {
//        List<Reservation> reservations = tempReservRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING);
//        return reservations.stream()
//                .map(TempDto::fromEntity)
//                .collect(Collectors.toList());
//    }

    public TempDto getReservationDetails(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        return TempDto.fromEntity(reservation);
    }
}
