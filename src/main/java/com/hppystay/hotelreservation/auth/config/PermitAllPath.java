package com.hppystay.hotelreservation.auth.config;

public class PermitAllPath {
    public static String[] paths = {
            // 회원 인증 관련 api
            "/api/auth/sign-up",
            "/api/auth/sign-in",
            // 숙소
            "test",
            // 문의사항
            "/api/hotel/inquiries/**"
    };
}
