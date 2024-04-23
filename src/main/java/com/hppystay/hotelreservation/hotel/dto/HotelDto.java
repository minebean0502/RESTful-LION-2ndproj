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
    private String name;
    private String address;
    private String region;
    private String description;
    private String images;
    private Double avg_score;
    private String mapX;
    private String mapY;
    private String phone;
    private List<RoomDto> rooms;
    private List<ReviewDto> reviews;

    public static HotelDto fromEntity(Hotel hotel) {
        List<RoomDto> roomDtoList = hotel.getRooms().stream()
                .map(RoomDto::fromEntity)
                .toList();

        return HotelDto.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .region(hotel.getRegion())
                .description(hotel.getDescription())
                .images(hotel.getImages())
                .avg_score(hotel.getAvg_score())
                .mapX(hotel.getMapX())
                .mapY(hotel.getMapY())
                .phone(hotel.getPhone())
                .rooms(roomDtoList)
                .build();
    }
}
