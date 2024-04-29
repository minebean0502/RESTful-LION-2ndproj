package com.hppystay.hotelreservation.hotel.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SearchDto {
    private String keyword;
    private LocalDate checkIn;
    private LocalDate checkOut;
}
