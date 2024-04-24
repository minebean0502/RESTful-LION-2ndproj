package com.hppystay.hotelreservation.auth.dto;

import lombok.Data;

@Data
public class PasswordChangeRequestDto {
    private String email;
    private String code;
    private String newPassword;
}
