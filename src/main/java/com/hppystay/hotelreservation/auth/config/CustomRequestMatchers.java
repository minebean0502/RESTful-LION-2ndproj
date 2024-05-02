package com.hppystay.hotelreservation.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CustomRequestMatchers {
    public static RequestMatcher[] permitAllMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-up"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-in"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/sign-up/send-code"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/email/verify"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/password/send-code"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/password/reset"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/login-info"),

            // Hotel
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/areaCode"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/keyword"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/location"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel"),
            RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/api/hotel/\\d+"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/search"),
            RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/api/hotel/search/\\d+\\?.*"),

            // 호텔 문의
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/inquiries/list"), //hotel 상세 페이지가 생기고 나면 없앨 것.
            RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/api/hotel/inquiries/\\d+"),


            // View
            AntPathRequestMatcher.antMatcher( "/login"),
            RegexRequestMatcher.regexMatcher(HttpMethod.GET, "/hotel/\\d+\\?.*"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/is-login"),
            AntPathRequestMatcher.antMatcher( "/denied"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/main"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/search"),


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
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/inquiries/submit/{hotelId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/submit"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/inquiries/update/{inquiryId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/inquiries/update/{inquiryId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/update"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/hotel/inquiries/inquiry/{inquiryId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/inquiries/comments/comment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/hotel/inquiries/comments/comment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/hotel/inquiries/comments/comment/{commentId}"),


            // Like
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/{hotelId}/like"),

            // view
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/my-page/**"),

            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/{hotelId}/review/list"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review/{reviewId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/{hotelId}/review/{reviewId}"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/{hotelId}/review/{reviewId}/update"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/{hotelId}/review/{reviewId}/delete"),


    };

    // 일반 USER 를 위한 Matcher
    public static AntPathRequestMatcher[] userMatchers = {
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests"),
            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/{hotelId}/review/{reviewId}/update"),
            AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/{hotelId}/review/{reviewId}/delete"),

            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/member/search/**"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/hotel/reservation/transfer/create"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/rooms"),

            // Toss
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/toss/confirm-payment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/toss/reservation/{id}/cancel"),
            AntPathRequestMatcher.antMatcher(HttpMethod.PATCH, "/api/hotel/cancel/reservation"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/toss/reservations"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/toss/reservations/{id}/payment"),

            // 양도 관련 (Hotel, Transfer, Toss 다 여깄음)
            // 혹시라도 transfered-by-member 부분에서 문제 생기면 assignment 이후에 /** 붙여두기
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/assignment"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/transfer/pending"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/reservation/pending"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/toss/payment/cancel-from-user"),

            // View
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/test/paymentComplete"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/test/paymentFail"),
            // TODO 어... my-page 윈도우는 /**로 처리했는데 이거 경로 설정 어떻게 해야함? 일단 몰라서 두개 추가함
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/payment/start/**"),

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
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/hotel/my"),
            // Review
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/{hotelId}/review/{reviewId}"),

            // View
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/management"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/create-view"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/hotel/update-view/{id}"),
    };

    // ADMIN 을 위한 Matcher
    public static AntPathRequestMatcher[] adminMatchers = {
            // Auth
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/manager-requests/list"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/manager-requests"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/approve"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/reject"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/admin"),

    };

    // 정적 자원을 위한 Matcher (인증을 요구하지 않도록 필터링)
    public static AntPathRequestMatcher[] resourcesMatcher = {
            // Resources
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/favicon.ico"),
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/static/**"),
    };

}


