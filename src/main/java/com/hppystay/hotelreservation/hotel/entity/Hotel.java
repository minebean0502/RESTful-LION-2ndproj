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
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id
    @Setter
    private String title; // 호텔명
    @Setter
    private String address;
    @Setter
    private String area;
    @Setter
    private String firstImage; // 대표 이미지 TODO: 사진을 여러개 처리하는 경우 LIST or String Split
    @Setter
    private String tel; // 숙소 연락처
    @Setter
    private String mapX; // 경도
    @Setter
    private String mapY; // 위도
    @Setter
    private String description; // 호텔 설명
    @Setter
    private Double avg_score = 0.0; // 평균 별점
    @Setter
    private Long review_count = 0L; //총 리뷰 개수
    @Setter
    private Long like_count = 0L; // 총 하트 개수

    @Setter
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();

    @Setter
    @OneToMany(mappedBy = "hotel")
    private List<Review> reviews = new ArrayList<>();

    @Setter
    @OneToOne
    private Member manager;

    @OneToMany(mappedBy = "hotel")
    private List<HotelLike> hotelLikes = new ArrayList<>();

    public Hotel addRoom(Room room) {
        this.getRooms().add(room);
        return this;
    }
}
