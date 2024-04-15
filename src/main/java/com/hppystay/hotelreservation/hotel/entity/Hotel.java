package com.hppystay.hotelreservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; // id
    @Setter
    String name; // 호텔명
    @Setter
    String description; // 호텔 설명
    @Setter
    String  images; // 대표 이미지 TODO: 사진을 여러개 처리하는 경우 LIST or String Split
    @Setter
    Double avg_score; // 별점
    @Setter
    Double mapX; // 경도
    @Setter
    Double mapY; // 위도
    @Setter
    String phone; // 숙소 연락처
//    @Setter
//    @OneToMany
//    Member manager_id;

}
