package com.hppystay.hotelreservation.payment.toss.service;

import com.hppystay.hotelreservation.payment.toss.dto.PaymentCancelDto;
import com.hppystay.hotelreservation.payment.toss.dto.PaymentConfirmDto;
import com.hppystay.hotelreservation.payment.toss.dto.PaymentDto;
import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import com.hppystay.hotelreservation.payment.toss.repository.PaymentRepository;
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
public class TossOrderService {
    private final TossHttpService tossService;
    private final PaymentRepository paymentRepository;
    private final TempReservationRepository reservationRepository;

    public Object confirmPayment(PaymentConfirmDto dto) {
        // 1. Object 형태로 DTO를 받습니다.
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());

        // orderName은 memberId - roomId 의 형태로 이뤄집니다
        // id = 1 / memberId = 1 / roomId = 500번방 -> orderName = 1-500번방
        // id = 2 / memberId = 2 / roomId = 1번방 -> orderName = 2-1번방
        // id = 4 / memberId = 2 / roomId = 10번방 -> orderName = 4-10번방
        String orderName = ((LinkedHashMap<String, Object>) tossPaymentObj)
                .get("orderName").toString();
        log.info(orderName);
        // [0]은 reservation의 PK // [1]은 reservation에 저장된 room의 Id
        // 일단 Reservation의 PK 추출
        Long reservationId = Long.parseLong(orderName.split("-")[0]);
        // 해당 PK로 Reservation 정보 추출 (Temp라서 나중에 로직상 사라질 부분입니다)
        // --------------------------------------------------------------------------- //
        TempReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
        // --------------------------------------------------------------------------- //

        // 필요한 정보만 뽑아옵니다
        return PaymentDto.fromEntity(paymentRepository.save(Payment.builder()
                .reservation(reservation)
                .tossPaymentKey(dto.getPaymentKey())
                .tossOrderId(dto.getOrderId())
                .status("DONE")
                .build()
        ));
    }
    public List<PaymentDto> readAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentDto::fromEntity)
                .toList();
    }

    public PaymentDto readOne(Long id) {
        return paymentRepository.findById(id)
                .map(PaymentDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // TossPaymentKey를 기준으로 결제상황 조회 (단건)
    public Object readTossPayment(Long id) {
        // Payment의 id(PK)를 기준으로 toss_payment_Key 조회
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Object response = tossService.getPayment(payment.getTossPaymentKey());
        // Object가 뭘 반환하고 있는지 체크
        log.info(response.toString());
        return response;
    }

    // cancelPayment
    // 취소는 paymentKey를 바탕으로 취소 진행 // 취소에는 사유가 필수
    @Transactional
    public Object cancelPayment(
            Long id,
            PaymentCancelDto dto
    ) {
        // 1. 취소할 주문을 찾는다.
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 2. 주문정보를 갱신한다.
        payment.setStatus("CANCEL");
        // 3. 취소후 결과를 응답한다.
        return tossService.cancelPayment(payment.getTossPaymentKey(), dto);
    }
}
