package com.hppystay.hotelreservation.hotel.service;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.AssignmentDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationDto;
import com.hppystay.hotelreservation.hotel.dto.ReservationInfoDto;
import com.hppystay.hotelreservation.hotel.entity.*;
import com.hppystay.hotelreservation.hotel.repository.AssignmentRepository;
import com.hppystay.hotelreservation.hotel.repository.HotelTransferRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import com.hppystay.hotelreservation.payment.toss.entity.TossPayment;
import com.hppystay.hotelreservation.payment.toss.repository.TossPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    private final TossPaymentRepository tossPaymentRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public ReservationDto createTransferReservation(ReservationDto request, Long grantorReservationId) {
        log.info("현재의 전달받은 request의 정보는 다음과 같습니다");
        log.info(request.toString());

        // [1] 양도 전에 필요한 정보들
        // 양도자(A)의 id를 검색
        // TODO reservationID 검색 뿐만 아니라, 결제가 완료된 건에 대해서만 진행한다고 가정할 것이므로
        // TODO ReservationStatus.RESERVATION_COMPLETED 인 것 만 찾아야함
        Reservation grantorReservation = hotelTransferRepository.findById(grantorReservationId)
                .orElseThrow(() -> new RuntimeException("Reservation not found"));
        // 해당 사용자의 예약 상태를 DONE -> 양도 진행중으로 변경
        grantorReservation.setStatus(ReservationStatus.ASSIGNMENT_PENDING);
        hotelTransferRepository.save(grantorReservation);

        // A의 reservation에서 tossPayment의 정보 가져와야함
        TossPayment tossPayment = (TossPayment) grantorReservation.getPayment();
        // 필요한 정보들인 (1)PaymentKey, (2)itemName, (3)toss_order_id(안씀), (4)트랜젝션키(옵션)
        String paymentKey = tossPayment.getTossPaymentKey();
        // String toss_order_id = tossPayment.getTossOrderId();
        // String last_transcation_key = tossPayment.getLastTransactionKey();
        Room room = roomRepository.findById(grantorReservation.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("룸을 찾지 못했습니다."));
        String roomName = room.getName();

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
        assignment.setPrice(tossPayment.getTotalAmount());
        // A의 reservation에서
        // 가격, A의 tossPaymentKey, itemName 가져와야함
        // TODO 사실 여기 orderId가 아니라 PaymentKey가 들어가서 이것저것 조금 달라짐
        assignment.setTossOrderId(paymentKey);
        assignment.setItemName(roomName);
        assignmentRepository.save(assignment);

        return ReservationDto.fromEntity(reservation);
    }

    // 4. 양도 관련 로직 진행
    public List<ReservationInfoDto> getPendingReservationsByMember() {
        // 현재 로그인 되어 있는 사용자의 정보로부터 시작
        Long memberId = facade.getCurrentMember().getId();

        /*
        // 예약 정보를 어떻게 찾을까? 이건 안쓸듯?
        Reservation reservation = reservationRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member not found"));

        log.info("service: "+ memberId);
        log.info("service: " + hotelTransferRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING));
        // 현재 로그인 된 사용자의 ID로, reservation이 PAYMENT_PENDING인 reservation 정보들을 찾음
        */

        return hotelTransferRepository.findByMemberIdAndStatus(memberId, ReservationStatus.PAYMENT_PENDING)
                .stream()
                .map(ReservationInfoDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 1. 닉네임이라 이메일로 찾는 서비스 로직
    public List<MemberDto> searchByNicknameOrEmail(String keyword) {
        List<Member> members = memberRepository.findByEmailContainingOrNicknameContaining(keyword, keyword);
        return members.stream().map(MemberDto::fromEntity).collect(Collectors.toList());
    }

    // 3. 해당 reservationId로 Assignment 정보 찾아서 일단 다 반환하기
    public AssignmentDto readAssignment(Long reservationId) {
        // B의 reservationId로 해당 reservation 찾기
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 reservationId로 Reservation을 찾을 수 없습니다"));

        // TODO 사실 facade로 찾을 수 있지않나?
        Member toMember = memberRepository.findById(reservation.getMember().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "to Member의 정보를 찾을 수 없습니다"));
        log.info(String.valueOf(toMember.getId()));
        Assignment assignment = assignmentRepository.findByToMember(toMember)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "reservation은 찾았으나, assignment를 찾을 수 없습니다"));
        AssignmentDto assignmentDto = AssignmentDto.fromEntity(assignment);
        log.info(String.valueOf(assignmentDto));
        return assignmentDto;
    }
}