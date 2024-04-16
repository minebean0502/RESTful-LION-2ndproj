package com.hppystay.hotelreservation.api.KNTO.dto.hotel;

import lombok.Data;

import java.util.List;

@Data
public class HotelResponseItemsDto {
    List<HotelResponseItemDto> item;
}
