package com.hppystay.hotelreservation.api.KNTO.dto.hotel;

import lombok.Data;

@Data
public class HotelResponseDto {
    private HotelResponseHeaderDto header;
    private HotelResponseBodyDto body;
}
