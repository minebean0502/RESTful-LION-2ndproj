package com.hppystay.hotelreservation.payment.toss.temp.entity;

import com.hppystay.hotelreservation.payment.toss.entity.Payment;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TempReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberEmail;
    private String roomId;
    private String numberOfPeople;
    private String price;
    private String status;
    private String imageUrl;

    // @ManyToOne
    // 본래라면 Reservation <-> Member은
    // Reservation -> Member (@ManyToOne)
    // Member -> Reservation (@OneToMany) 양방향 관계가 성립해야 하나, 테스트 목적이라 Long로 빼둠
    private Long member;

    // 표시되지는 않으나 존재함
    @Setter
    private String payment;


//    @Column(name = "payment_id")
//    private Long paymentId;
    // 예약서에는
    // PK와, 정보들과
    // Member의 Member ID와
    // Room에서 가져온 price가 있다
}
