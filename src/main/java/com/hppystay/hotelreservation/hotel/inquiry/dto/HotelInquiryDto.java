package com.hppystay.hotelreservation.hotel.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelInquiryDto {
    private Integer id;
    private String title;
    private String content;
    private CommentDto comment;
    private Long writerId;
    private String writer;
    private Integer hotelId;
    private LocalDateTime createdAt;
}
