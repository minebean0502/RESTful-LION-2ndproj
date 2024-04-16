package com.hppystay.hotelreservation.api.KNTO.dto.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SimpleHotel {

    private String title;
    private String address;
    private String areaName;
    private String contentName;
    private String firstImage;
    private String tel;
    private double mapX;
    private double mapY;
}
