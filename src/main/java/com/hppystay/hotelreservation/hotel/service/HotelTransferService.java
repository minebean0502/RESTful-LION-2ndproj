package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.entity.*;
import com.hppystay.hotelreservation.hotel.repository.AssignmentRepository;
import com.hppystay.hotelreservation.hotel.repository.HotelTransferRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelTransferService {
    private final HotelTransferRepository hotelTransferRepository;
    private final MemberRepository memberRepository;
    private final RoomRepository roomRepository;
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

        Member fromUser = facade.getCurrentMember();

        Assignment assignment = new Assignment();
        assignment.setReservation(grantorReservation);
        assignment.setFromUser(fromUser);
        assignment.setToUser(member);
        assignment.setStatus(AssignmentStatus.PENDING);
        assignmentRepository.save(assignment);


        return ReservationDto.fromEntity(reservation);
    }

    public List<ReservationDto> getPendingReservationsByMember() {
        Long memberId = facade.getCurrentMember().getId();
        List<Reservation> reservations = hotelTransferRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING);
        return reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }
}
