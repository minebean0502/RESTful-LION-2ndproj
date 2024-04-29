package com.hppystay.hotelreservation.auth.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String email;
    private String password;
}
