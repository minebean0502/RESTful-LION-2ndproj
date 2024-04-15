package com.hppystay.hotelreservation.api.KNTO.service;

import com.hppystay.hotelreservation.api.KNTO.dto.HotelFindDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.Map;

@Component
@HttpExchange
public interface HotelComponent {
    @GetExchange("/searchStay1")
    HotelFindDto SearchHotel(
            @RequestParam
            Map<String, Object> params
    );

}
