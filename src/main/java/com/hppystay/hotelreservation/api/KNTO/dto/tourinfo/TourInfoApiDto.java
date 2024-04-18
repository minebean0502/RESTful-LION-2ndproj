package com.hppystay.hotelreservation.api.KNTO.dto.tourinfo;


import com.hppystay.hotelreservation.api.KNTO.utils.AreaCode;
import com.hppystay.hotelreservation.api.KNTO.utils.ContentCode;
import lombok.*;


@Getter
@Builder
public class TourInfoApiDto {

    private String title;
    private String address;
    private int areaCode;
    private int contentTypeId;
    private String firstImage;
    private String tel;
    private String mapX;
    private String mapY;

    public SimpleTourInfo toResponse() {
        return SimpleTourInfo.builder()
                .title(title)
                .address(address)
                .areaName(AreaCode.getAreaName(areaCode))
                .contentName(ContentCode.getContentName(contentTypeId))
                .firstImage(firstImage)
                .tel(tel)
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }
}
