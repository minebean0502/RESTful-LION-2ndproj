package com.hppystay.hotelreservation.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PasswordDto {
    private String currentPassword;
    private String newPassword;
}

