package com.hppystay.hotelreservation.hotel.inquiry.mapper;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.dto.HotelInquiryDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;

public class HotelInquiryMapper {
    public static HotelInquiryDto toDto(HotelInquiry hotelInquiry) {
        if (hotelInquiry == null) {
            return null;
        }

        CommentDto commentDto = null;
        Comment comment = hotelInquiry.getComment();
        if (comment != null) {
            commentDto = CommentMapper.toDto(comment);
        }

        return HotelInquiryDto.builder()
                .id(hotelInquiry.getId())
                .title(hotelInquiry.getTitle())
                .content(hotelInquiry.getContent())
                .writerId(hotelInquiry.getWriterId())
                .writer(hotelInquiry.getWriter())
                .hotelId(hotelInquiry.getHotelId())
                .comment(commentDto)
                .createdAt(hotelInquiry.getCreatedAt())
                .build();
    }

    public static HotelInquiry toEntity(HotelInquiryDto hotelInquiryDto) {
        if (hotelInquiryDto == null) {
            return null;
        }

        HotelInquiry hotelInquiry = new HotelInquiry();
        //hotelInquiry.setId(hotelInquiryDto.getId());
        hotelInquiry.setTitle(hotelInquiryDto.getTitle());
        hotelInquiry.setContent(hotelInquiryDto.getContent());
        hotelInquiry.setWriterId(hotelInquiryDto.getWriterId());
        hotelInquiry.setWriter(hotelInquiryDto.getWriter());
        hotelInquiry.setHotelId(hotelInquiryDto.getHotelId());

        return hotelInquiry;
    }
}
