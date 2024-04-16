package com.hppystay.hotelreservation.api.KNTO.service;

import com.hppystay.hotelreservation.api.KNTO.dto.hotel.HotelFindDto;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@HttpExchange
public interface KNTORepositoryService {
    @GetExchange("/searchStay1")
    HotelFindDto SearchHotel(
            @RequestParam
            Map<String, Object> params
    );

}
