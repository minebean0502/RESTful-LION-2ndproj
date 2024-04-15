package com.hppystay.hotelreservation.api.KNTO.dto;

import lombok.Data;

import java.util.List;

@Data
public class HotelResponseItemsDto {
    List<HotelResponseItemDto> item;
}
