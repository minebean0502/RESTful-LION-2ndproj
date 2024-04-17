package com.hppystay.hotelreservation.api.KNTO.utils;

import java.util.HashMap;
import java.util.Map;

public class AreaCode {

    static Map<Integer, String> areaCodeMap = new HashMap<>();


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
}
