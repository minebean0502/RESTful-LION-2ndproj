package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final ApiService apiService;

    @GetMapping("/test/areaCode/{areaCode}")
    public List<TourInfoApiDto> findHotelByRegion(@PathVariable("areaCode") Integer areaCode){
       return apiService.callHotelByRegionApi(areaCode);
    }

    @GetMapping("/test/keyword/{keyword}")
    public List<TourInfoApiDto> findHotelByKeyWord(@PathVariable("keyword") String keyword) {
        return apiService.callHotelByKeywordApi(keyword);
    }

    @GetMapping("/test/location/{mapX}/{mapY}")
    public List<TourInfoApiDto> findSpotByLocation(
            @PathVariable("mapX")
            String mapX,
            @PathVariable("mapY")
            String mapY
    ) {
        return apiService.callSpotByLocationApi(mapX, mapY);
    }

}
