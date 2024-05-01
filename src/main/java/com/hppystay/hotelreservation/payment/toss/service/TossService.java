package com.hppystay.hotelreservation.payment.toss.service;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.entity.Assignment;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.ReservationStatus;
import com.hppystay.hotelreservation.hotel.repository.AssignmentRepository;
import com.hppystay.hotelreservation.hotel.repository.ReservationRepository;
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

    @Transactional
    public Object confirmPayment(TossPaymentConfirmDto dto) {
        Member member = facade.getCurrentMember();
        // 1. Object 형태로 DTO를 받습니다.
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info("정보를 확인합시다");
        log.info(tossPaymentObj.toString());
        // orderName은 memberId - roomId 의 형태로 이뤄집니다
        String orderNameInfo = ((LinkedHashMap<String, Object>) tossPaymentObj).get("orderName").toString();
        // Long reservationId = Long.parseLong(orderNameInfo.split("-")[0]);
        String requestedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("requestedAt").toString();
        String approvedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("approvedAt").toString();
        String lastTransactionKey = ((LinkedHashMap<String, Object>) tossPaymentObj).get("lastTransactionKey").toString();

        // TODO reservation을 찾을 때, 멤버가 여러개의 예약을 할 수 있으니까 더 정확히 찾기위한 포인트는
        // memberId와, roomId가 일치하는 애여야함
        // TODO repository 나중에 한번 갈아줘야함
        Reservation reservation = reservationRepository.findByMember(member);
        log.info("현재 진행하는 reservation의 id는: " + reservation.getId());

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


    // 실질적으로 이 이하의 Service는 아이템 Order과 관련되어 있음
    public List<TossPaymentDto> readAll() {
        return tossPaymentRepository.findAll().stream()
                .map(TossPaymentDto::fromEntity)
                .toList();
    }

    //
    public TossPaymentDto readOne(Long id) {
        return tossPaymentRepository.findById(id)
                .map(TossPaymentDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // TossPaymentKey를 기준으로 결제상황 조회 (단건)
    public Object readTossPayment(Long id) {
        // Payment의 id(PK)를 기준으로 toss_payment_Key 조회
        TossPayment tossPayment = tossPaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Object response = tossService.getPayment(tossPayment.getTossPaymentKey());
        // Object가 뭘 반환하고 있는지 체크
        log.info(response.toString());
        return response;
    }

    // cancelPayment
    // 취소는 paymentKey를 바탕으로 취소 진행 // 취소에는 사유가 필수
    @Transactional
    public Object cancelPayment(
            Long id,
            TossPaymentCancelDto dto
    ) {
        // 1. 취소할 주문을 찾는다.
        TossPayment tossPayment = tossPaymentRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 2. 주문정보를 갱신한다.
        if (!(tossPayment.getStatus().equals("CANCEL"))) {
            tossPayment.setStatus("CANCEL");
            // 3. 취소후 결과를 응답한다.
            return tossService.cancelPayment(tossPayment.getTossPaymentKey(), dto);
        }
        else {
            // 이미 cancel건에 대해 있을 경우 HttpStatus CONFLICT = 이미 취소된 건에 대하여 또 취소 신청 에러값
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment is already canceled");
        }
    }

    // 이건 B의 결제가 끝나고나면 자동으로 A의 결제를 취소하는 로직
    @Transactional
    public Object cancelPaymentBeforeUser(TossPaymentCancelDto dto) {
        // 먼저 B의 reservationId로 정보들을 찾아야함
        // 취소하기 위한 정보들은 A의 tosspayment 정보
        // 따라서 assignment 에 접근해야함 (접근하려면, a의 memberId, b의 memberId, a의 reservationId인데)
        // 가능한건 b의 memberId를 이용해서 접근 가능
        Member toMember = facade.getCurrentMember();
        // 현재 멤버를 조회했으니 이를 사용해서 assignment에 접근해야함
        Assignment assignment = assignmentRepository.findByToMember(toMember)
                .orElseThrow(() -> new RuntimeException("assignment가 없습니다"));

        // 그리고 여기서부터 취소할 A의 주문을 찾고 갱신하고 취소를 진행해야함
        TossPayment fromMemberTossPayment = tossPaymentRepository.findById(assignment.getReservation().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이전 사용자의 정보를 찾을 수 없어요"));
        Reservation reservation = reservationRepository.findById(assignment.getReservation().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "<1>reservation 못찾았음"));
        // A의 주문 정보를 갱신함
        log.info("현재 A의 tossPayment의 status는 어떤가요");
        log.info(fromMemberTossPayment.getStatus());
        if (!fromMemberTossPayment.getStatus().equals("CANCEL")) {
            fromMemberTossPayment.setStatus("CANCEL");
            reservation.setStatus(ReservationStatus.ASSIGNMENT_COMPLETED);
            log.info("A의 상태가 어떻게 바뀌었나요");
            log.info(fromMemberTossPayment.getStatus());
            return tossService.cancelPayment(fromMemberTossPayment.getTossPaymentKey(), dto);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "주문 정보를 갱신/취소 할 수 없었음");
    }
}
