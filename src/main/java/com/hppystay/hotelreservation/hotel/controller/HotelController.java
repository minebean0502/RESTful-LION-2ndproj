package com.hppystay.hotelreservation.hotel.controller;

import com.hppystay.hotelreservation.api.KNTO.dto.hotel.HotelApiDto;
import com.hppystay.hotelreservation.api.service.ApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotelController {

    private final ApiService apiService;

    @GetMapping("/test/{areaCode}")
    public List<HotelApiDto> test(@PathVariable("areaCode") Integer areaCode) throws IOException {
       return apiService.callHotelRegionApi(areaCode);
    }

}
