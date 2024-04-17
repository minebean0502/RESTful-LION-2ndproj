package com.hppystay.hotelreservation.auth.entity;

import com.hppystay.hotelreservation.hotel.entity.Reservation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();

}