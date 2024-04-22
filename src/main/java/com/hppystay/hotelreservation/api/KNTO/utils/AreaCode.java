package com.hppystay.hotelreservation.api.KNTO.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AreaCode {

    private final static Map<Integer, String> areaCodeMap = new HashMap<>();

    public AreaCode() {
        areaCodeMap.put(1, "서울");
        areaCodeMap.put(2, "인천");
        areaCodeMap.put(3, "대전");
        areaCodeMap.put(4, "대구");
        areaCodeMap.put(5, "광주");
        areaCodeMap.put(6, "부산");
        areaCodeMap.put(7, "울산");
        areaCodeMap.put(8, "세종");
        areaCodeMap.put(31, "경기");
        areaCodeMap.put(32, "강원");
        areaCodeMap.put(33, "충북");
        areaCodeMap.put(34, "충남");
        areaCodeMap.put(35, "경북");
        areaCodeMap.put(36, "경남");
        areaCodeMap.put(37, "전북");
        areaCodeMap.put(38, "전남");
        areaCodeMap.put(39, "제주");
    }

    public static String getAreaName(int areaCode) {
       return areaCodeMap.get(areaCode);
    }

    public static int getAreaCode(String name) {

        if(areaCodeMap.entrySet().stream()
                .noneMatch(entry -> entry.getValue().equals(name)))
            return 0;

        else
        {
        return areaCodeMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(Map.Entry::getKey)
                .toList()
                .get(0); // 지역명에 매칭되는 코드는 1개이므로 첫번째 원소 반환
        }
    }
}
