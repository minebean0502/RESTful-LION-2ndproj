package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.auth.entity.CustomUserDetails;
import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 이 컨트롤러는 호텔 문의사항에 대한 댓글을 다루는 API 엔드포인트를 제공합니다.
 * 사용자는 문의사항에 댓글을 작성, 수정, 삭제할 수 있습니다.
 */
@RestController
@RequestMapping("/api/hotel/inquiries/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    /**
     * 문의사항에 대해 새로운 댓글을 작성합니다.
     * @param userDetails 현재 로그인한 사용자의 정보
     * @param inquiryId 댓글을 달 문의사항의 ID
     * @param commentDto 댓글의 내용을 담은 DTO
     * @return 성공 시 "comment created successfully" 메시지와 함께 OK 응답을 반환
     */
    @PostMapping("/submit/{inquiryId}")
    public ResponseEntity<?> submitComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer inquiryId,
            @RequestBody CommentDto commentDto
    ) {
        String writerId = userDetails.getUsername();
        commentService.createComment(commentDto, writerId, inquiryId);
        return ResponseEntity.ok().body("comment created successfully");
    }

    /**
     * 사용자의 댓글을 수정합니다.
     * 댓글 ID와 새로운 댓글 내용을 기반으로 기존 댓글을 업데이트 합니다.
     *
     * @param userDetails 현재 로그인한 사용자의 정보
     * @param commentId 수정할 댓글의 ID
     * @param commentDto 새로운 댓글 내용을 담은 DTO
     * @return 성공 시 "comment updated successfully" 메시지와 함께 OK 응답을 반환
     */
    //TODO CORS -> PUT
    @PostMapping("/update/{commentId}")
    public ResponseEntity<?> updateComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CommentDto commentDto
    ) {
        String currentUsername = userDetails.getUsername();
        commentService.updateComment(commentId, commentDto, currentUsername);
        return ResponseEntity.ok().body("comment updated successfully");
    }

    /**
     * 사용자가 작성한 댓글을 삭제합니다.
     * 댓글 ID를 기반으로 댓글을 찾아 데이터베이스에서 삭제합니다.
     *
     * @param commentId 삭제할 댓글의 ID
     * @param currentUser 현재 로그인한 사용자의 정보
     * @return 성공 시 "comment deleted successfully" 메시지와 함께 OK 응답을 반환
     */
    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable("commentId") Integer commentId,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        commentService.deleteComment(commentId, currentUser.getUsername());
        return ResponseEntity.ok().body("comment deleted successfully");
    }
}
