package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Hotel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HotelDto {

    private Long id;
    private String name;
    private String region;
    private String description;
    private String images;
    private Double avg_score;
    private String mapX;
    private String mapY;
    private String phone;

    public HotelDto fromEntity(Hotel entity) {
        return HotelDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .region(entity.getRegion())
                .description(entity.getDescription())
                .images(entity.getImages())
                .avg_score(entity.getAvg_score())
                .mapX(entity.getMapX())
                .mapY(entity.getMapY())
                .phone(entity.getPhone())
                .build();
    }
}
