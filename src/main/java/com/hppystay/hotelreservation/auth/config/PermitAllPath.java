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
            "/api/oauth2/get-token",

            // toss 인증 관련 api (채운 작성)
            "/toss/**",
            "/payments/**",
            "/reservations/**",
            "/reservation/**",

            // toss API 관련 (이하 삭제 금지)
            // reservations.html에 요청하는 자바스크립트 부분 (없으면 리스트 표시안됨)
            // fetch('/api/reservations/lists')
            "/api/reservations/**",

            "/main",

            // static/templates 에 이미지 표시 경로
            "/static/tossImage/**",
            "/static/templates/**",
            "/static/css/**",
            "/static/templates/tempHotel/**",

            // 숙소
            "/test/**",
            "/test/view-test",
            "test/keyword/**",
            "test/areaCode/**",
            "/hotel/",
            "/hotel/**",
            "/hotel/room/**",
//            "create-hotel",

            // 문의사항
            "/hotel/inquiries/**",
            "/api/hotel/inquiries/**",


            // View
            "/login",
            "/sign-up",


            "/token/callback",
            "/is-login",
            "/main",
            "/denied",


            // resources
            "/favicon.ico",


            // 리뷰
            "/api/{hotelId}/review/**"

    };
}
