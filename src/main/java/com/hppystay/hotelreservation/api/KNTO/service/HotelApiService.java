package com.hppystay.hotelreservation.api.KNTO.service;

import com.hppystay.hotelreservation.api.KNTO.dto.HotelFindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HotelApiService {
    private final HotelComponent hotelComponent;

    public HotelFindDto findHotelList() {
        Map<String, Object> params = new HashMap<>();
        params.put("numOfRows", 10);
        params.put("pageNo", 1);
        params.put("MobileOS", "ETC");
        params.put("MobileApp", "RL");
        params.put("_type", "json");
        params.put("listYN", "Y");
        params.put("arrange", "A");
        params.put("areaCode", "");
        params.put("sigunguCode", "");
        params.put("modifiedtime", "");
        params.put("serviceKey", "S/TpfnoGESi+4PILoTQdr7lSM6JpVeOrrFwqBT4ObUmSH85RY6FgqtNXfHbWkD1R5ynQ5JgXUHJM7YiCYNgcKA==");

        return hotelComponent.SearchHotel(params);
    }
}
