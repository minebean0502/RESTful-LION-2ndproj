package com.hppystay.hotelreservation.api.KNTO.dto.hotel;


import com.hppystay.hotelreservation.api.KNTO.utils.AreaCode;
import com.hppystay.hotelreservation.api.KNTO.utils.ContentCode;
import lombok.*;


@Data
@AllArgsConstructor
@Builder
public class HotelApiDto {

    private String title;
    private String address;
    private int areaCode;
    private int contentTypeId;
    private String firstImage;
    private double mapX;
    private double mapY;

    public SimpleHotel toResponse() {
        return SimpleHotel.builder()
                .title(title)
                .address(address)
                .areaName(AreaCode.getAreaName(areaCode))
                .contentName(ContentCode.getContentName(contentTypeId))
                .firstImage(firstImage)
                .mapX(mapX)
                .mapY(mapY)
                .build();
    }
}
