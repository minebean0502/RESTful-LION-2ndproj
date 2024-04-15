package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.HotelFindDto;
import com.hppystay.hotelreservation.api.KNTO.service.HotelApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final HotelApiService hotelApiService;

    @GetMapping("/test")
    public HotelFindDto test() {
        return hotelApiService.findHotelList();
    }

}
