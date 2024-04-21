package com.hppystay.hotelreservation.payment.kakao.service;

import com.hppystay.hotelreservation.payment.kakao.dto.Request.KaKaoPaymentReadyDto;
import com.hppystay.hotelreservation.payment.kakao.repository.KaKaoRepository;
import com.hppystay.hotelreservation.payment.toss.temp.entity.TempReservationEntity;
import com.hppystay.hotelreservation.payment.toss.temp.repository.TempReservationRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {
    private final KakaoHttpService kakaoService;
    private final KaKaoRepository kaKaoRepository;
    private final TempReservationRepository reservationRepository;

    // cid 테스트 코드는 공개용이므로 일단 넣어줌
    static final String cid = "TC0ONETIME";

    public Object readyPayment(KaKaoPaymentReadyDto dto) {
        // 처음에 가맹점 코드는 테스트 cid가 있으므로 그것을 담아서 보내줌.
        log.info("partner_order_id 설정중");
        String partner_order_id = String.valueOf(UUID.randomUUID());
        log.info("partner_order_id 설정완료");
        log.info(partner_order_id);

        log.info("dto cid set");
        dto.setCid(cid);
        log.info("dto order_id set");
        dto.setPartner_order_id(partner_order_id);

//        // --------------------------------------------------------------------------- //
//        TempReservationEntity reservation = reservationRepository.findById(reservationId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));
//        // --------------------------------------------------------------------------- //

        Object kakaoPaymentObj = kakaoService.readyPayment(dto);
        log.info(kakaoPaymentObj.toString());
        // 작성중
        return kakaoPaymentObj;
    }

}
