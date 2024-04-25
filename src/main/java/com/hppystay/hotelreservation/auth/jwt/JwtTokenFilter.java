package com.hppystay.hotelreservation.auth.jwt;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.repository.MemberRepository;
import com.hppystay.hotelreservation.auth.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final MemberService memberService;

    public JwtTokenFilter(JwtTokenUtils jwtTokenUtils, MemberService memberService) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

//        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.split(" ")[1];
//            if (jwtTokenUtils.validate(token)) {
//                String userEmail = jwtTokenUtils.parseClaims(token).getSubject();
//
//                SecurityContext context
//                        = SecurityContextHolder.createEmptyContext();
//
//                UserDetails userDetails = memberService.loadUserByUsername(userEmail);
//
//                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails,
//                        token, userDetails.getAuthorities());
//                context.setAuthentication(authentication);
//                SecurityContextHolder.setContext(context);
//            } else {
//                log.warn("jwt validation failed");
//            }
//        }

        // HttpServletRequest에서 쿠키 배열 가져오기
        Cookie[] cookies = request.getCookies();
        Cookie accessCookie = null, refreshCookie = null;
        String accessToken, refreshToken;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("accessToken".equals(cookie.getName())) {
                    accessCookie = cookie;
                } else if ("refreshToken".equals(cookie.getName())) {
                    refreshCookie = cookie;
                }
            }
        }

        // accessCookie가 존재하지 않는 경우 refreshToken으로부터 재발급
        if (accessCookie == null) {
            if (refreshCookie != null) {
                refreshToken = refreshCookie.getValue();
                if (jwtTokenUtils.validate(refreshToken)) {
                    String userEmail = jwtTokenUtils.parseClaims(refreshToken).getSubject();

                    if (memberService.userExists(userEmail)) {
                        UserDetails userDetails = memberService.loadUserByUsername(userEmail);
                        Member member = ((CustomUserDetails) userDetails).getMember();

                        // 액세스 토큰 재발급시 리프레쉬 토큰도 재발급
                        accessToken = jwtTokenUtils.generateAccessToken(member);
                        refreshToken = jwtTokenUtils.generateRefreshToken(member);
                        refreshCookie.setValue(refreshToken);
                        accessCookie = new Cookie("accessToken", accessToken);
                        accessCookie.setHttpOnly(true);
                        accessCookie.setPath("/");
                        accessCookie.setMaxAge(60 * 5);

                        response.addCookie(accessCookie);
                        response.addCookie(refreshCookie);
                    } else {
                        log.error("refreshToken 회원 정보 없음");
                        refreshCookie.setMaxAge(0);
                        response.addCookie(refreshCookie);
                    }
                } else {
                    log.error("잘못된 refreshToken 토큰입니다.");
                    refreshCookie.setMaxAge(0);
                    response.addCookie(refreshCookie);
                }
            }
        }

        if (accessCookie != null) {
            accessToken = accessCookie.getValue();
            if (jwtTokenUtils.validate(accessCookie.getValue())) {
                String userEmail = jwtTokenUtils.parseClaims(accessToken).getSubject();

                if (memberService.userExists(userEmail)) {
                    UserDetails userDetails = memberService.loadUserByUsername(userEmail);

                    SecurityContext context
                            = SecurityContextHolder.createEmptyContext();

                    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            accessToken, userDetails.getAuthorities());
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);

                } else {
                    // 혹시 서버 재시작 등의 이유로 token에 해당하는 멤버가 없는 경우
                    log.error("회원 정보 없음");
                    accessCookie.setMaxAge(0);
                    response.addCookie(accessCookie);
                    if (refreshCookie != null) {
                        refreshCookie.setMaxAge(0);
                        response.addCookie(refreshCookie);
                    }
                }
            } else {
                log.warn("jwt validation failed");
            }
        }

        filterChain.doFilter(request, response);
    }
}
