package com.hppystay.hotelreservation.auth.config;

public class PermitAllPath {
    public static String[] paths = {
            // 회원 인증 관련 api
            "/api/auth/sign-up",
            "/api/auth/sign-in",
            "/api/auth/sign-up/send-code",
            "/api/auth/email/verify",
            "/api/auth/password/send-code",
            "/api/auth/password/reset",
            "/api/auth/password/change",

            // toss 인증 관련 api (채운 작성)
            "/toss/**",
            "/payments/**",
            "/reservations/**",
            "/reservation/**",
            "/api/toss/**",

            // toss API 관련 (이하 삭제 금지)
            "/api/reservations/**",

            // 숙소
            "/hotel/create-view",
            "/api/hotel/keyword/**",
            "/api/hotel/areaCode/**",
            "/api/hotel/",
            "/api/hotel/**",
            "/api/hotel/room/**",
            "/api/hotel/reservation",

            // 문의사항
            "/hotel/inquiries/**",
            "/api/hotel/inquiries/**",


            // View
            "/login",
            "/sign-up",
            "/password-reset",

            "/token/callback",
            "/is-login",
            "/main",
            "/denied",
            "/hotel/1/details",
            "/hotel/**",

            // resources
            "/favicon.ico",
            "/static/**",


            // 리뷰
            "/api/{hotelId}/review/**",

    };
}
