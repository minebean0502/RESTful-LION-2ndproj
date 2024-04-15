package com.hppystay.hotelreservation.hotel.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelInquiryDto {
    private Integer id;
    private String title;
    private String content;
    private List<CommentDto> comments;
    private Integer writerId;
    private Integer hotelId;
}
