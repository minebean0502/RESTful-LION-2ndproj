package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Entity(name = "likes")
@SuperBuilder
@NoArgsConstructor
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    public Like(Member member, Hotel hotel) {
        this.member = member;
        this.hotel = hotel;
    }
}
