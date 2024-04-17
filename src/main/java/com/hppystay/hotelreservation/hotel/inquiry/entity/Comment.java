package com.hppystay.hotelreservation.hotel.inquiry.entity;

import com.hppystay.hotelreservation.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Setter
    private String comment;

    @OneToOne
    @JoinColumn(name = "inquiry_id")
    private HotelInquiry hotelInquiry;

    @Setter
    private String writerId;


}
