package com.hppystay.hotelreservation.auth.entity;

import com.hppystay.hotelreservation.common.entity.BaseEntity;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.entity.Reservation;
import com.hppystay.hotelreservation.hotel.entity.HotelLike;
import com.hppystay.hotelreservation.hotel.review.Review;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    @Column(unique = true)
    private String email;
    @Setter
    private String password;

    @Setter
    private String profileImage;

    @Setter
    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Setter
    private String businessNumber;

    @OneToMany(mappedBy = "member")
    private List<Reservation> reservationList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(mappedBy = "manager")
    @Setter
    private Hotel hotel;

    @OneToMany(mappedBy = "member")
    private List<HotelLike> hotelLikeList = new ArrayList<>();
    
}
