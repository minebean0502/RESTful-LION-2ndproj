package com.hppystay.hotelreservation.hotel.inquiry.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    private Integer id;
    private String comment;
    private Long writerId;
    private String writer;
    private Integer inquiryId;
    private LocalDateTime createdAt;

}
