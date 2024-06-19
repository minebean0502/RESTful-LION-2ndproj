package com.hppystay.hotelreservation.payment.toss.service;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.entity.Assignment;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import com.hppystay.hotelreservation.hotel.entity.Room;
import com.hppystay.hotelreservation.hotel.repository.AssignmentRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
import com.hppystay.hotelreservation.hotel.repository.RoomRepository;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentCancelDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.dto.TossPaymentDto;
import com.hppystay.hotelreservation.payment.toss.entity.TossPayment;
import com.hppystay.hotelreservation.payment.toss.repository.TossPaymentRepository;
import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import com.hppystay.hotelreservation.payment.toss.temp.repository.TempReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossService {
    private final TossHttpService tossService;
    private final TossPaymentRepository tossPaymentRepository;
    private final ReservationRepository reservationRepository;
    private final AuthenticationFacade facade;
    private final AssignmentRepository assignmentRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Object confirmPayment(Long roomId, Long reservationId, TossPaymentConfirmDto dto) {
        // TODO 서버<->Toss서버간 결제에 있어서 과연 어떤 사용자인지 알 필요가 있는가?
        Member member = facade.getCurrentMember();
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.RESERVATION_ROOM_NOT_FOUND));
        // 1. Object 형태로 DTO를 받습니다.
        Object tossPaymentObj = tossService.confirmPayment(dto);

        String requestedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("requestedAt").toString();
        String approvedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("approvedAt").toString();
        String lastTransactionKey = ((LinkedHashMap<String, Object>) tossPaymentObj).get("lastTransactionKey").toString();

        // TODO 현재 접속중인 멤버 + reservationId = 찾는 reservation이여야함
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 reservation을 찾을 수 없습니다"));
        TossPayment tossPayment = tossPaymentRepository.save(TossPayment.builder()
                .reservation(reservation)
                .reservationId(reservation.getId())
                .tossPaymentKey(dto.getPaymentKey())
                .tossOrderId(dto.getOrderId())
                .totalAmount(dto.getAmount())
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .lastTransactionKey(lastTransactionKey)
                .category("Toss")
                .status("DONE")
                .build());

        // 2. 그 뒤 reservation에 Payment id 추가하기
        reservation.setPayment(tossPayment);
        reservation.setStatus(ReservationStatus.RESERVATION_COMPLETED);
        TossPaymentDto tossPaymentDto = TossPaymentDto.fromEntity(tossPayment);
        // 3. dto 반환
        return tossPaymentDto;
    }

    // 결제 취소
    @Transactional
    public Object cancelPayment(
            Long reservationId,
            TossPaymentCancelDto dto
    ) {
        // 1. reservationId 취소할 주문을 찾는다.
        TossPayment tossPayment = tossPaymentRepository.findById(reservationId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.RESERVATION_CANNOT_FOUND));
        // 2. 주문정보를 갱신한다.
        if (!(tossPayment.getStatus().equals("CANCEL"))) {
            tossPayment.setStatus("CANCEL");
            // 3. 취소후 결과를 응답한다.
            return tossService.cancelPayment(tossPayment.getTossPaymentKey(), dto);
        }
        else {
            // 이미 cancel건에 대해 있을 경우 HttpStatus CONFLICT = 이미 취소된 건에 대하여 또 취소 신청 에러값
            throw new GlobalException(GlobalErrorCode.RESERVATION_DECLINED_ALREADY);
        }
    }

    // 이건 B의 결제가 끝나고나면 자동으로 A의 결제를 취소하는 로직
    @Transactional
    public Object cancelPaymentBeforeUser(TossPaymentCancelDto dto) {
        // 현재 로그인 한 사용자가 양수자
        Member toMember = facade.getCurrentMember();
        Assignment assignment = assignmentRepository.findByToMember(toMember)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.ASSIGNMENT_CANNOT_FOUND));

        // 양도자의 정보를 바탕으로 (1) 결제수단, (2) 예약 정보를 찾기
        TossPayment fromMemberTossPayment = tossPaymentRepository.findById(assignment.getToReservation().getId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.TRANSMITTER_CANNOT_FOUND));
        Reservation reservation = reservationRepository.findById(assignment.getToReservation().getId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.TRANSMITTER_RESERVATION_CANNOT_FOUND));

        // 양도자의 주문 정보를 갱신함
        if (!fromMemberTossPayment.getStatus().equals("CANCEL")) {
            fromMemberTossPayment.setStatus("CANCEL");
            reservation.setStatus(ReservationStatus.ASSIGNMENT_COMPLETED);
            return tossService.cancelPayment(fromMemberTossPayment.getTossPaymentKey(), dto);
        } else throw new GlobalException(GlobalErrorCode.ASSIGNMENT_CANNOT_UPDATED);
    }

    // 결제 수단 전체 조회
    public List<TossPaymentDto> readAll() {
        return tossPaymentRepository.findAll().stream()
                .map(TossPaymentDto::fromEntity)
                .toList();
    }

    // 결제 수단 단건 조회
    public TossPaymentDto readOne(Long id) {
        return tossPaymentRepository.findById(id)
                .map(TossPaymentDto::fromEntity)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.PAYMENT_CANNOT_FOUND));
    }

    // TossPaymentKey를 기준으로 결제상황 조회 (단건)
    public Object readTossPayment(Long id) {
        // Payment의 id(PK)를 기준으로 toss_payment_Key 조회
        TossPayment tossPayment = tossPaymentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.PAYMENT_KEY_CANNOT_FOUND));
        Object response = tossService.getPayment(tossPayment.getTossPaymentKey());
        return response;
    }
}
