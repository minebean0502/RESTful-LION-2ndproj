package com.hppystay.hotelreservation.auth.config;

import com.hppystay.hotelreservation.auth.handler.CustomAuthenticationEntrypoint;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenFilter;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import com.hppystay.hotelreservation.auth.oauth2.OAuth2SuccessHandler;
import com.hppystay.hotelreservation.auth.oauth2.OAuth2UserService;
import com.hppystay.hotelreservation.auth.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberService memberService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2UserService oAuth2UserService;
    private final CustomAuthenticationEntrypoint authenticationEntrypoint;


    @Bean
    public SecurityFilterChain securityFilterChai(
            HttpSecurity http
    ) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(CustomRequestMatchers.permitAllMatchers)
                                .permitAll()
                                .requestMatchers(CustomRequestMatchers.authenticatedMatchers)
                                .authenticated()
                                .requestMatchers(CustomRequestMatchers.userMatchers)
                                .hasRole("USER")
                                .requestMatchers(CustomRequestMatchers.managerMatchers)
                                .hasRole("MANAGER")
                                .requestMatchers(CustomRequestMatchers.adminMatchers)
                                .hasRole("ADMIN")
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .successHandler(oAuth2SuccessHandler)
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService))
                )
                .logout(configure -> configure
                        .deleteCookies("accessToken", "refreshToken")
                        .logoutSuccessUrl("/main")
                )
                .exceptionHandling(configurer -> configurer
                        .authenticationEntryPoint(authenticationEntrypoint)
                        .accessDeniedPage("/denied")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        new JwtTokenFilter(jwtTokenUtils, memberService),
                        AuthorizationFilter.class
                );

        return http.build();
    }
}