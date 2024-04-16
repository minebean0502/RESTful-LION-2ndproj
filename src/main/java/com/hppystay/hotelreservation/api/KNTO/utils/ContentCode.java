package com.hppystay.hotelreservation.api.KNTO.utils;

public class ContentCode {

    public static final int 관광지 = 12;
    public static final int 숙박 = 32;
    public static final int 여행코스 = 25;
    public static final int 음식점 = 39;

    public static String getContentName(Integer contentCode) {
        if (contentCode == ContentCode.관광지) return "관광지";
        else if (contentCode == ContentCode.여행코스) return "여행코스";
        else if (contentCode == ContentCode.숙박) return "숙박";
        else if (contentCode == ContentCode.음식점) return "음식점";
        else return "기타";
    }
}
