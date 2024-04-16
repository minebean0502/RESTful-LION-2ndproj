package com.hppystay.hotelreservation.api.KNTO.utils;

import java.util.HashMap;
import java.util.Map;

public class ContentCode {

    static Map<Integer, String> areaCodeMap = new HashMap<>();

    public static final int 관광지 = 12;
    public static final int 숙박 = 32;
    public static final int 여행코스 = 25;
    public static final int 음식점 = 39;

    public ContentCode() {
        areaCodeMap.put(12, "관광지");
        areaCodeMap.put(32, "숙박");
        areaCodeMap.put(25, "여행코스");
        areaCodeMap.put(39, "음식점");
    }

    public static String getContentName(int areaCode) {
        return areaCodeMap.get(areaCode);
    }

}
