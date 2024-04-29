package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;
    @Setter
    private String price;
    @Setter
    private String content;

    @Setter
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;
}
