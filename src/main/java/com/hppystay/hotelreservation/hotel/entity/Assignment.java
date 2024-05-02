package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assignment")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "from_member_id")
    private Member fromMember;  // A가 될거임

    @Setter
    @ManyToOne
    @JoinColumn(name = "to_member_id")
    private Member toMember;    // B가 될거임

    @Setter
    @OneToOne
    @JoinColumn(name = "from_reservation_id")
    private Reservation fromReservation;

    @Setter
    @OneToOne
    @JoinColumn(name = "to_reservation_id")
    private Reservation toReservation;

    // 양도할 데이터 필드
    @Setter
    private String price; // 결제 금액
    @Setter
    private String tossOrderId; // Toss 결제의 주문 ID
    @Setter
    private String itemName; // 예약된 상품명 (Room의 이름)

//    // 추가적으로 양도 시점의 정보 기록이 필요하다면
//    private LocalDateTime assignedAt; // 양도 요청 시점
}



