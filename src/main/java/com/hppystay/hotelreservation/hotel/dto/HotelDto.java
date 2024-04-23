package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.review.ReviewDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class HotelDto {

    private Long id;
    private String title;
    private String address;
    private String area;
    private String firstImage;
    private String tel;
    private String mapX;
    private String mapY;

    private String description; // 사용자가 추가
    private Double avg_score; // 리뷰 생성 시 갱신
    private List<RoomDto> rooms; // 사용자가 추가
    private List<ReviewDto> reviews;

    public static HotelDto fromEntity(Hotel hotel) {
        List<RoomDto> roomDtoList = hotel.getRooms().stream()
                .map(RoomDto::fromEntity)
                .toList();

        return HotelDto.builder()
                .id(hotel.getId())
                .title(hotel.getTitle())
                .address(hotel.getAddress())
                .area(hotel.getArea())
                .description(hotel.getDescription())
                .firstImage(hotel.getFirstImage())
                .avg_score(hotel.getAvg_score())
                .mapX(hotel.getMapX())
                .mapY(hotel.getMapY())
                .tel(hotel.getTel())
                .rooms(roomDtoList)
                .build();
    }
}