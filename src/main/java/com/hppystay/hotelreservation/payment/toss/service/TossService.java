package com.hppystay.hotelreservation.payment.toss.service;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
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

    @Transactional
    public Object confirmPayment(TossPaymentConfirmDto dto) {
        // 1. Object 형태로 DTO를 받습니다.
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());

        // orderName은 memberId - roomId 의 형태로 이뤄집니다
        String orderNameInfo = ((LinkedHashMap<String, Object>) tossPaymentObj).get("orderName").toString();
        Long reservationId = Long.parseLong(orderNameInfo.split("-")[0]);
        String requestedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("requestedAt").toString();
        String approvedAt = ((LinkedHashMap<String, Object>) tossPaymentObj).get("approvedAt").toString();
        String lastTransactionKey = ((LinkedHashMap<String, Object>) tossPaymentObj).get("lastTransactionKey").toString();

        // 해당 PK로 Reservation 정보 추출 (Temp라서 나중에 로직상 사라질 부분입니다)
        // --------------------------------------------------------------------------- //
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        // --------------------------------------------------------------------------- //
        // 1. 일단 tosspayment에 저장정보 생성
        TossPayment tossPayment = tossPaymentRepository.save(TossPayment.builder()
                .reservation(reservation)
                .reservationId(reservationId)
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


//    public Object assignConfirmPayment() {
//
//    }
}
