package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.entity.*;
import com.hppystay.hotelreservation.hotel.repository.AssignmentRepository;
import com.hppystay.hotelreservation.hotel.repository.HotelTransferRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelTransferService {
    private final HotelTransferRepository hotelTransferRepository;
    private final MemberRepository memberRepository;
    private final AssignmentRepository assignmentRepository;
    private final AuthenticationFacade facade;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationDto createTransferReservation(ReservationDto request, Long grantorReservationId) {
        // 양도자(A)의 id를 검색
        Reservation grantorReservation = hotelTransferRepository.findById(grantorReservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        // 해당 사용자의 예약 상태를 DONE -> 양도 진행중으로 변경
        grantorReservation.setStatus(ReservationStatus.ASSIGNMENT_PENDING);
        hotelTransferRepository.save(grantorReservation);

        // 양도받을 멤버(B) 확인
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // 양수자(B)의 예약 정보 생성
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        // 체크인과 체크아웃 설정해줌
        reservation.setCheckIn(grantorReservation.getCheckIn());
        log.info(String.valueOf(grantorReservation.getCheckIn()));
        reservation.setCheckOut(grantorReservation.getCheckOut());
        log.info(String.valueOf(grantorReservation.getCheckOut()));
        // 결제 진행중으로 바꾸고, 전달받은 Dto를 통해 방을 배정
        // 중요, 결제 리스트 불러오는것과, 양도 리스트 불러오는것에 중복이 생길 수 있으므로 분리
        reservation.setStatus(ReservationStatus.ASSIGNMENT_PAYMENT_PENDING);
        reservation.setRoom(grantorReservation.getRoom());
        hotelTransferRepository.save(reservation);

        // 현재 로그인한 사람이 양도자니까 찾고
        Member fromMember = facade.getCurrentMember();

        // Assignment 객체 생성 및 관련 정보 저장
        Assignment assignment = new Assignment();
        assignment.setReservation(grantorReservation);
        assignment.setFromMember(fromMember);
        assignment.setToMember(member);
        assignmentRepository.save(assignment);


        return ReservationDto.fromEntity(reservation);
    }

    // 3. 양도 관련 로직 진행
    public List<ReservationInfoDto> getPendingReservationsByMember() {
        // 현재 로그인 되어 있는 사용자의 정보로부터 시작
        Long memberId = facade.getCurrentMember().getId();

        // 예약 정보를 어떻게 찾을까?
        Reservation reservation = reservationRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member not found"));



        log.info("service: "+ memberId);
        log.info("service: " + hotelTransferRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING));
        return hotelTransferRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING)
                .stream()
                .map(ReservationInfoDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<MemberDto> searchByNicknameOrEmail(String keyword) {
        List<Member> members = memberRepository.findByEmailContainingOrNicknameContaining(keyword, keyword);
        return members.stream().map(MemberDto::fromEntity).collect(Collectors.toList());
    }
}
