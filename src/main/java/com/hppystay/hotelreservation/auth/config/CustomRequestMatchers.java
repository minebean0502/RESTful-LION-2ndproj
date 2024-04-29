package com.hppystay.hotelreservation.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CustomRequestMatchers {
    public static AntPathRequestMatcher[] permitAllMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-up"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-in"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-up/send-code"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/email/verify"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/password/send-code"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/password/reset"),

            // Hotel
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/areaCode/{area}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/keyword/{keyword}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/location/{mapX}{mapY}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/{id}"),

            // 호텔 문의
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/inquiries/list"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/inquiries/{hotelId}"),


            // View
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/login"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/create-view"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/is-login"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/denied"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/main"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/search"),

            //임의 추가
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/{hotelId}/details/m1"),

            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/{hotelId}/review/list"),


    };

    // 인증된 사용자를 위한 Matcher
    public static AntPathRequestMatcher[] authenticatedMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/auth/password/change"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/my-profile"),

            // Hotel
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/profile-upload"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/reservation"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/my"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/reservation/transfer"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/pending"),

            // 호텔 문의
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/submit/{hotelId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/update/{hotelId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/delete/{hotelId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/comments/submit/{inquiryId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/comments/update/{inquiryId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/comments/delete/{inquiryId}"),


            // Like
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/likes/{hotelId}"),

            // view
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/my-page/**"),

            // Admin (테스트 위해 잠시-> 테스트 끝나면 ADMIN쪽으로 옮기기)
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/admin"),

//            // Review
//            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review"),
//            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/{hotelId}/review/list"),

    };

    // 일반 USER 를 위한 Matcher
    public static AntPathRequestMatcher[] userMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests"),

            // Review

            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/{hotelId}/review/{reviewId}/update"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/{hotelId}/review/{reviewId}/delete"),

            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/member/search/**"),
            // 고민 부분 (어디까지 허용할지)
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/reservation/transfer/create"),

            // Toss
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/toss/confirm-payment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/toss/reservations"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/toss/reservations/{id}/payment"),

            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/"),

            // 양도 관련 (Hotel, Transfer, Toss 다 여깄음)
            // 혹시라도 transfered-by-member 부분에서 문제 생기면 assignment 이후에 /** 붙여두기
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/assignment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/pending"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/pending"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/toss/payment/cancel-from-user"),

            // View
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/test/paymentComplete"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/test/paymentFail"),

            // 끝나면 삭제할것
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/test"),

    };

    // MANAGER 를 위한 Matcher
    public static AntPathRequestMatcher[] managerMatchers = {
            // Hotel
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/hotel/{id}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/hotel/{id}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/{id}"),

            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review/{reviewId}"),
    };

    // ADMIN 을 위한 Matcher
    public static AntPathRequestMatcher[] adminMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/manager-requests/list"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/approve"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/reject"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/{hotelId}/review/{reviewId}/delete"),

            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review/{reviewId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/{hotelId}/review/{reviewId}/update"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/{hotelId}/review/{reviewId}/delete"),
    };

    // 정적 자원을 위한 Matcher (인증을 요구하지 않도록 필터링)
    public static AntPathRequestMatcher[] resourcesMatcher = {
            // Resources
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/favicon.ico"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/static/**"),
    };
}