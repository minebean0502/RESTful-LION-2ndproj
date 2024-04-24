package com.hppystay.hotelreservation.hotel.entity;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import com.hppystay.hotelreservation.hotel.review.Review;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String title; // 호텔명

    private String address;

    private String area;

    private String firstImage; // 대표 이미지 TODO: 사진을 여러개 처리하는 경우 LIST or String Split

    private String tel; // 숙소 연락처

    private String mapX; // 경도

    private String mapY; // 위도

    private String description; // 호텔 설명

    private Double avg_score; // 평균 별점

    private Long review_count; //총 리뷰 개수

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews = new ArrayList<>();

    @Setter
    @OneToOne
    private Member manager;

    public Hotel addRoom(Room room) {
        this.getRooms().add(room);
        return this;
    }
}
