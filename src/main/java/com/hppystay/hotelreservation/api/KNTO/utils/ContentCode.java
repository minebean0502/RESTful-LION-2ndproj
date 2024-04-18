package com.hppystay.hotelreservation.api.KNTO.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ContentCode {

    private final static Map<Integer, String> contentCodeMap = new HashMap<>();

    public ContentCode() {
        contentCodeMap.put(12, "관광지");
        contentCodeMap.put(14, "문화시설");
        contentCodeMap.put(15, "축제공연행사");
        contentCodeMap.put(25, "여행코스");
        contentCodeMap.put(28, "레포츠");
        contentCodeMap.put(32, "숙박");
        contentCodeMap.put(38, "쇼핑");
        contentCodeMap.put(39, "음식점");
    }

    public static String getContentName(int areaCode) {
        return contentCodeMap.get(areaCode);
    }

    public static int getContentCode(String name) {
        return contentCodeMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(name))
                .map(Map.Entry::getKey)
                .toList()
                .get(0); // 지역명에 매칭되는 코드는 1개이므로 첫번째 원소 반환
    }
}
