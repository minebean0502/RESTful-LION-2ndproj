package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.hotel.HotelFindDto;
import com.hppystay.hotelreservation.api.KNTO.service.KNTOApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HotelController {
    private final KNTOApiService hotelApiService;

    @GetMapping("/test")
    public HotelFindDto test() {
        return hotelApiService.findHotelList();
    }

}
