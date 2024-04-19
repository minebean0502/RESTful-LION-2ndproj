package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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

    @GetMapping("/token")
    public Claims val(@RequestParam("token") String token) {
        return jwtTokenUtils.parseClaims(token);
    }

    @GetMapping("/uuid")
    public String uuid(@RequestParam("uuid") String uuid) {
        return redisTemplate.opsForValue().get(uuid);
    }

    @GetMapping("/redis")
    public String test() {
        ValueOperations<String, String> operations
                = redisTemplate.opsForValue();

        operations.set("토큰", "리프레시 토큰", 10, TimeUnit.SECONDS);
        return "test";
    }
}
