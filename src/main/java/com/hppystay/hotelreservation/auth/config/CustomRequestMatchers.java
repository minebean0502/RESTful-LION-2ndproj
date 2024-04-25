package com.hppystay.hotelreservation.auth.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CustomRequestMatchers {
    // 일반 USER 를 위한 Matcher
    public static AntPathRequestMatcher[] userMatchers = {
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests")
    };

    // MANAGER 를 위한 Matcher
    public static AntPathRequestMatcher[] managerMatchers = {
            
    };

    // ADMIN 을 위한 Matcher
    public static AntPathRequestMatcher[] adminMatchers = {
            AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/auth/manager-requests"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/approve"),
            AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/manager-requests/{requestId}/reject"),
    };

    // 인증된 사용자를 위한 Matcher
    public static AntPathRequestMatcher[] authenticatedMatchers = {
        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/auth/profile-upload")
    };
}
