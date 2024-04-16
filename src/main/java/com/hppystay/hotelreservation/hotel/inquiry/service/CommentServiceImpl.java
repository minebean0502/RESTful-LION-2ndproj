package com.hppystay.hotelreservation.hotel.inquiry.service;


import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.repository.CommentRepository;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final HotelInquiryRepository hotelInquiryRepository;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository,
            HotelInquiryRepository hotelInquiryRepository
    ) {
        this.commentRepository = commentRepository;
        this.hotelInquiryRepository = hotelInquiryRepository;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer writerId) {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(commentDto.getInquiryId())
                .orElseThrow(() -> new IllegalStateException("Inquiry with id " + commentDto.getInquiryId() + " not found."));

        if (hotelInquiry.getComment() != null) {
            throw new IllegalStateException("A comment for this inquiry already exists.");
        }

        Comment comment = Comment.builder()
                .comment(commentDto.getComment())
                .hotelInquiry(hotelInquiry)
                .writerId(writerId)
                .build();

        Comment savedComment = commentRepository.save(comment);
        return convertEntityToDto(savedComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByInquiryId(Long inquiryId) {
        return commentRepository.findByHotelInquiryId(inquiryId)
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("Comment with id " + commentId + " does not exist."));
        existingComment.setComment(commentDto.getComment());
        Comment updatedComment = commentRepository.save(existingComment);
        return convertEntityToDto(updatedComment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentRepository.deleteById(commentId);
    }

    private CommentDto convertEntityToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .writerId(comment.getWriterId())
                .inquiryId(comment.getHotelInquiry().getId())
                .build();
    }
}
