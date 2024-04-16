package com.hppystay.hotelreservation.hotel.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Table(name = "hotel_inquiry")
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

    @Setter
    @OneToOne(mappedBy = "hotelInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Comment comment;

    @Setter
    private Integer writerId;
    @Setter
    private Integer hotelId;
}
