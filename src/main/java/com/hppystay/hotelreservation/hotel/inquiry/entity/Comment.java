package com.hppystay.hotelreservation.hotel.inquiry.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String content;

    @ManyToOne
    @JoinColumn(name = "inquiry_id")
    private HotelInquiry hotelInquiry;

    @Setter
    private Integer writerId;

}
