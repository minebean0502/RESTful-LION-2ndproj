package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.dto.MemberDto;
import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenUtils jwtTokenUtils;
    private final StringRedisTemplate redisTemplate;
    private final AuthenticationFacade facade;

    @GetMapping("/login")
    public ResponseEntity<MemberDto> isLogin() {
        return ResponseEntity.ok(MemberDto.fromEntity(facade.getCurrentMember()));
    }
}
