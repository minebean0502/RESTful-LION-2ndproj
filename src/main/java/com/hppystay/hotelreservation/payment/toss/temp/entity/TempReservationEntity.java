package com.hppystay.hotelreservation.payment.toss.temp.entity;

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


    // 예약서에는
    // PK와, 정보들과
    // Member의 Member ID와
    // Room에서 가져온 price가 있다
}
