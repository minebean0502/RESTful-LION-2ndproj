package com.hppystay.hotelreservation.hotel.dto;

import com.hppystay.hotelreservation.hotel.entity.Room;
import lombok.*;

@Getter
@Builder
public class RoomDto {

    private Long id;
    private Long hotelId;
    private String name;
    private String price; //TODO: Integer or String
    private String content;

    public static RoomDto fromEntity(Room entity) {
        return RoomDto.builder()
                .id(entity.getId())
                .hotelId(entity.getHotel().getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .content(entity.getContent())
                .build();
    }
}
