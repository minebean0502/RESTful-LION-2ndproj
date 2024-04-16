package com.hppystay.hotelreservation.hotel.inquiry.service;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;

import java.util.List;

public interface CommentService {
    List<CommentDto> getAllCommentsByInquiryId(Long inquiryId);
    CommentDto getCommentById(Integer id);
    CommentDto createComment(CommentDto commentDto, Integer writerId);
    CommentDto updateComment(Integer commentId, CommentDto commentDto);
    void deleteComment(Integer commentId);
}
