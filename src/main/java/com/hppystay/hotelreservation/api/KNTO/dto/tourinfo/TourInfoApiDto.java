package com.hppystay.hotelreservation.api.KNTO.dto.tourinfo;

import lombok.*;


@Getter
@Builder
@ToString
public class TourInfoApiDto {
    private String title;
    private String address;
    private int areaCode;
    private String area;
    private int contentTypeId;
    private String content;
    private String firstImage;
    private String tel;
    private String mapX;
    private String mapY;
}
