package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto, Integer writerId);
    List<CommentDto> getAllCommentsByInquiryId(Long inquiryId);
    CommentDto updateComment(Integer commentId, CommentDto commentDto);
    void deleteComment(Integer commentId);
}
