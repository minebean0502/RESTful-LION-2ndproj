package com.hppystay.hotelreservation.hotel.inquiry.service;


import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .id(commentDto.getId()) // 이 부분은 주로 null이 될 것입니다, 자동 생성을 위해
                .build();
        // 여기서 writerId와 inquiryId는 엔티티로의 변환 처리가 필요할 수 있습니다.
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
        existingComment.setContent(commentDto.getContent());
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
                .content(comment.getContent())
                .writerId(comment.getWriterId())
                .inquiryId(comment.getHotelInquiry().getId())
                .build();
    }
}
