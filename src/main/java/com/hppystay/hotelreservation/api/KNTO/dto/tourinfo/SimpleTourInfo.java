package com.hppystay.hotelreservation.api.KNTO.dto.tourinfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SimpleTourInfo {

    private String title;
    private String address;
    private String areaName;
    private String contentName;
    private String firstImage;
    private String tel;
    private String mapX;
    private String mapY;
}
