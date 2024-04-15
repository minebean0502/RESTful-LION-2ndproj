package com.hppystay.hotelreservation.api.KNTO.dto;

import lombok.Data;

@Data
public class HotelResponseBodyDto {
    private HotelResponseItemsDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}