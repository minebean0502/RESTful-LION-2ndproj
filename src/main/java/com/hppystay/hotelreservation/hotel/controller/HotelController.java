package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.tourinfo.TourInfoApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import com.hppystay.hotelreservation.hotel.dto.HotelDto;
import com.hppystay.hotelreservation.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final ApiService apiService;
    private final HotelService hotelService;

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

    @PostMapping("/hotel")
    public HotelDto createHotel(
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.createHotel(dto);
    }

    @GetMapping("/hotel/{id}")
    public HotelDto readHotel(
            @PathVariable("id")
            Long id
    ) {
        return hotelService.readOneHotel(id);
    }

    @PutMapping("/hotel/{id}")
    public HotelDto updateHotel(
            @PathVariable("id")
            Long id,
            @RequestBody
            HotelDto dto
    ) {
        return hotelService.updateHotel(id, dto);
    }

    @DeleteMapping("/hotel/{id}")
    public void deleteHotel(
            @PathVariable("id")
            Long id
    ) {
        hotelService.deleteHotel(id);
    }





}
