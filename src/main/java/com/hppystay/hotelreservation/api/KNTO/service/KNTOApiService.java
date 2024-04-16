package com.hppystay.hotelreservation.api.KNTO.service;

import com.hppystay.hotelreservation.api.KNTO.dto.hotel.HotelFindDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class KNTOApiService {
    private final KNTORepositoryService hotelComponent;
    @Value("${KNTO_KEY}")
    String serviceKey;

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
        params.put("serviceKey", serviceKey);

        return hotelComponent.SearchHotel(params);
    }
}
