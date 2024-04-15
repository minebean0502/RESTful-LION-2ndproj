package com.hppystay.hotelreservation.hotel.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "hotel-inquiry")
@NoArgsConstructor
@AllArgsConstructor
public class HotelInquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String title;
    @Setter
    private String content;

    @OneToMany(mappedBy = "hotelInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Setter
    private Integer writerId;
    @Setter
    private Integer hotelId;
}
