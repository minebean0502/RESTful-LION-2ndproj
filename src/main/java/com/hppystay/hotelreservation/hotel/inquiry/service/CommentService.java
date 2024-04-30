package com.hppystay.hotelreservation.hotel.inquiry.service;


import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.mapper.CommentMapper;
import com.hppystay.hotelreservation.hotel.inquiry.repository.CommentRepository;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.OperationNotAllowedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.PermissionDeniedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final HotelInquiryRepository hotelInquiryRepository;
    private final AuthenticationFacade facade;


    public ResponseEntity<?> createComment(CommentDto commentDto) {
        HotelInquiry hotelInquiry = findHotelInquiryById(commentDto.getInquiryId());

        if (hotelInquiry.getComment() != null) {
            throw new OperationNotAllowedException("A comment for this inquiry already exists.");
        }

        Comment comment = CommentMapper.toEntity(commentDto);
        comment.setHotelInquiry(hotelInquiry);
        comment.setWriterId(facade.getCurrentMember().getId());
        comment.setWriter(facade.getCurrentMember().getEmail());

        commentRepository.save(comment);

        return ResponseEntity.ok(Map.of("message", "Comment created successfully"));
    }

    public List<CommentDto> getAllCommentsByInquiryId(Long inquiryId) {
        return commentRepository.findByHotelInquiryId(inquiryId)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> updateComment( CommentDto commentDto) {
        Comment existingComment = findCommentById(commentDto.getId());
        checkPermission(existingComment.getWriterId(), facade.getCurrentMember().getId());
        existingComment.setComment(commentDto.getComment());

        return ResponseEntity.ok(Map.of("message", "comment updated successfully"));
    }

    public ResponseEntity<?> deleteComment(Integer commentId) {
        Comment comment = findCommentById(commentId);
        checkPermission(comment.getWriterId(), facade.getCurrentMember().getId());

        HotelInquiry inquiry = comment.getHotelInquiry();
        if (inquiry != null) {
            inquiry.setComment(null); // HotelInquiry에서 Comment 연결 해제
            hotelInquiryRepository.save(inquiry); // 변경사항 저장
        }

        commentRepository.deleteById(commentId);

        return ResponseEntity.ok().body("comment deleted successfully");
    }

    private Comment findCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " does not exist."));
    }

    private HotelInquiry findHotelInquiryById(Integer id) {
        return hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry with id " + id + " not found."));
    }

    private void checkPermission(Long writerId, Long currentUsername) {
        if (!writerId.equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to perform this action.");
        }
    }

}
