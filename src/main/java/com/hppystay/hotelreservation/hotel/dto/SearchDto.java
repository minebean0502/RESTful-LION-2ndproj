package com.hppystay.hotelreservation.hotel.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SearchDto {
    private String keyword;
    private LocalDate checkIn;          // `YYYY-MM-DD` = 2024-05-01
    private LocalDate checkOut;         // `YYYY-MM-DD` = 2024-05-01
}
