package com.hppystay.hotelreservation.hotel.inquiry.mapper;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;

public class CommentMapper {

    public static CommentDto toDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .writerId(comment.getWriterId())
                .writer(comment.getWriter())
                .inquiryId(comment.getHotelInquiry() != null ? comment.getHotelInquiry().getId() : null)
                .createdAt(comment.getCreatedAt())
                .build();
    }

    public static Comment toEntity(CommentDto commentDto) {
        if (commentDto == null) {
            return null;
        }

        Comment comment = new Comment();
        //comment.setId(commentDto.getId());
        comment.setComment(commentDto.getComment());
        comment.setWriterId(commentDto.getWriterId());
        comment.setWriter(commentDto.getWriter());
        return comment;
    }
}
