package com.hppystay.hotelreservation.hotel.inquiry.entity;

import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@Table(name = "hotel_inquiry")
@NoArgsConstructor
@AllArgsConstructor
public class HotelInquiry extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    @OneToOne(mappedBy = "hotelInquiry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Comment comment;
    private String writerId;
    private Integer hotelId;
}
