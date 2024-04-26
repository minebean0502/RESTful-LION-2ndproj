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

    @Transactional
    public ReservationDto createTransferReservation(ReservationDto request, Long grantorReservationId) {
        Reservation grantorReservation = hotelTransferRepository.findById(grantorReservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        grantorReservation.setStatus(ReservationStatus.ASSIGNMENT_PENDING);
        hotelTransferRepository.save(grantorReservation);

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));
        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setStatus(ReservationStatus.PAYMENT_PENDING);
        reservation.setRoom(grantorReservation.getRoom());
        hotelTransferRepository.save(reservation);

        Member fromMember = facade.getCurrentMember();

        Assignment assignment = new Assignment();
        assignment.setReservation(grantorReservation);
        assignment.setFromMember(fromMember);
        assignment.setToMember(member);
        assignmentRepository.save(assignment);


        return ReservationDto.fromEntity(reservation);
    }

    public List<ReservationInfoDto> getPendingReservationsByMember() {
        Long memberId = facade.getCurrentMember().getId();
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
