package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import jakarta.persistence.*;
import lombok.*;

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
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

    @Setter
    @ManyToOne
    @JoinColumn(name = "from_user", referencedColumnName = "id")
    private Member fromUser;

    @Setter
    @ManyToOne
    @JoinColumn(name = "to_user", referencedColumnName = "id")
    private Member toUser;

    @Setter
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;


}
