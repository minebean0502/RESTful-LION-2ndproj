package com.hppystay.hotelreservation.hotel.inquiry.service;


import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.entity.Comment;
import com.hppystay.hotelreservation.hotel.inquiry.entity.HotelInquiry;
import com.hppystay.hotelreservation.hotel.inquiry.mapper.CommentMapper;
import com.hppystay.hotelreservation.hotel.inquiry.repository.CommentRepository;
import com.hppystay.hotelreservation.hotel.inquiry.repository.HotelInquiryRepository;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.OperationNotAllowedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.PermissionDeniedException;
import com.hppystay.hotelreservation.hotel.inquiry.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
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
    public void createComment(CommentDto commentDto, String writerId, Integer inquiryId) {
        HotelInquiry hotelInquiry = findHotelInquiryById(inquiryId);

        if (hotelInquiry.getComment() != null) {
            throw new OperationNotAllowedException("A comment for this inquiry already exists.");
        }

        Comment comment = CommentMapper.toEntity(commentDto);
        comment.setHotelInquiry(hotelInquiry);
        comment.setWriterId(writerId);

        commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsByInquiryId(Long inquiryId) {
        return commentRepository.findByHotelInquiryId(inquiryId)
                .stream()
                .map(CommentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Integer id) {
        Comment comment = findCommentById(id);
        return CommentMapper.toDto(comment);
    }

    @Override
    public void updateComment(Integer commentId, CommentDto commentDto, String currentUsername) {
        Comment existingComment = findCommentById(commentId);
        checkPermission(existingComment.getWriterId(), currentUsername);
        existingComment.setComment(commentDto.getComment());
    }

    @Override
    public void deleteComment(Integer commentId, String currentUsername) {
        Comment comment = findCommentById(commentId);
        checkPermission(comment.getWriterId(), currentUsername);

        HotelInquiry inquiry = comment.getHotelInquiry();
        if (inquiry != null) {
            inquiry.setComment(null); // HotelInquiry에서 Comment 연결 해제
            hotelInquiryRepository.save(inquiry); // 변경사항 저장
        }

        commentRepository.deleteById(commentId);
    }

    private Comment findCommentById(Integer id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + id + " does not exist."));
    }

    private HotelInquiry findHotelInquiryById(Integer id) {
        return hotelInquiryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inquiry with id " + id + " not found."));
    }

    private void checkPermission(String writerId, String currentUsername) {
        if (!writerId.equals(currentUsername)) {
            throw new PermissionDeniedException("You do not have permission to perform this action.");
        }
    }

}
