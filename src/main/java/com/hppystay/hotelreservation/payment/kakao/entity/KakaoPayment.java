package com.hppystay.hotelreservation.payment.kakao.entity;

import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class KakaoPayment extends Payment{
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String status;
    @Setter
    private String category;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private TempReservationEntity reservation;
    를 상속받은 상태
     */
    private Long reservationId;
    private String cid;                 // 가맹점 코드는 일정함
    private String tid;                 // 결제 고유번호
    private String partner_order_id;    // 가맹점 상품 주문번호
    private String partner_user_id;     // 주문 상품 유저 번호
    private String item_name;           // 가맹점 상품 이름명
    private String quantity;            // 상품 갯수 (1로 처리할지, reservation 방 가격 * 인원수의 인원수로 처리할지 논의)
    private String pg_token;            // 일단 모르니 기입 (필요해보임)
    private String aid;                 // 요청 고유번호?
}
