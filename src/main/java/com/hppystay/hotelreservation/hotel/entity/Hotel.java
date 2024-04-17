package com.hppystay.hotelreservation.hotel.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // id

    private String name; // 호텔명

    private String address;

    private String region;

    private String description; // 호텔 설명

    private String  images; // 대표 이미지 TODO: 사진을 여러개 처리하는 경우 LIST or String Split

    private Double avg_score; // 별점

    private Double mapX; // 경도

    private Double mapY; // 위도

    private String phone; // 숙소 연락처

    @OneToMany(mappedBy = "hotel")
    private List<Room> rooms = new ArrayList<>();

//    @Setter
//    @OneToMany
//    Member manager_id;

}
