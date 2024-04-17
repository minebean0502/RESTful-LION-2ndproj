package com.hppystay.hotelreservation.hotel.inquiry.service;


import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.repository.CommentRepository;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
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
    public CommentDto createComment(CommentDto commentDto, Integer writerId, Integer inquiryId) {
        HotelInquiry hotelInquiry = hotelInquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalStateException("Inquiry with id " + inquiryId + " not found."));

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
    public CommentDto getCommentById(Integer id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Comment with id " + id + "does not exist."));
        return convertEntityToDto(comment);
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalStateException("Comment with id " + commentId + " does not exist."));
        existingComment.setComment(commentDto.getComment());
        Comment updatedComment = commentRepository.save(existingComment);
        return convertEntityToDto(updatedComment);
    }

    @Transactional
    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId));

        HotelInquiry inquiry = comment.getHotelInquiry();
        if (inquiry != null) {
            inquiry.setComment(null); // HotelInquiry에서 Comment 연결 해제
            hotelInquiryRepository.save(inquiry); // 변경사항 저장
        }

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
