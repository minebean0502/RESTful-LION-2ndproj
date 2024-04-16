package com.hppystay.hotelreservation.auth.controller;

import com.hppystay.hotelreservation.auth.jwt.JwtTokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/token")
    public Claims val(@RequestParam("token") String jwt) {
        return jwtTokenUtils.parseClaims(jwt);
    }
}
