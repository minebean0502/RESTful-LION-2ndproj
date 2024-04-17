package com.hppystay.hotelreservation.auth.config;

public class PermitAllPath {
    public static String[] paths = {
            // 회원 인증 관련 api
            "/api/auth/sign-up",
            "/api/auth/sign-in",
            "/api/auth/email/verify",
            "/api/auth/password/find",
            "/api/auth/password/reset",
            "/api/auth/password/change",

            // toss 인증 관련 api (채운 작성)
            "/toss/**",
            "/payments/**",
            "/reservations/**",

            // toss API 관련 (이하 삭제 금지)
            // reservations.html에 요청하는 자바스크립트 부분 (없으면 리스트 표시안됨)
            // fetch('/api/reservations/lists')
            "/api/reservations/**",

            // static/templates 에 이미지 표시 경로
            "/static/**",
            "/templates/**",

            // 숙소
            "test",
            "/test/**",
            "/login"

    };
}
