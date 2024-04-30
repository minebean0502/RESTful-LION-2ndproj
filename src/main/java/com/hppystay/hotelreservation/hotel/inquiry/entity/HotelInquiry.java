package com.hppystay.hotelreservation.hotel.inquiry.entity;

import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@Table(name = "hotel_inquiry")
@NoArgsConstructor
@AllArgsConstructor
public class HotelInquiry extends BaseEntity {
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
    private Long writerId;
    @Setter
    private String writer;
    @Setter
    private Integer hotelId;
}
