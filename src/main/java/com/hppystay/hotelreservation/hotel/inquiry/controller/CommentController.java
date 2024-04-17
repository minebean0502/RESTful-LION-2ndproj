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


@RestController
@RequestMapping("/api/hotel/inquiries/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/submit/{inquiryId}")
    public ResponseEntity<CommentDto> submitComment(
            //TODO 로그인한 사용자 ID 가져오기
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Integer inquiryId,
            @RequestBody CommentDto commentDto
    ) {
        String writerId = userDetails.getUsername();
        CommentDto createdComment = commentService.createComment(commentDto, writerId, inquiryId);

        return ResponseEntity.ok(createdComment);
    }

    //TODO CORS -> PUT
    @PostMapping("/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CommentDto commentDto
    ) {
        String currentUsername = userDetails.getUsername();
        CommentDto updatedComment = commentService.updateComment(commentId, commentDto, currentUsername);
        return ResponseEntity.ok(updatedComment);
    }

    //TODO CORS -> DELETE -> list.html의 javascript에서도 POST->DELETE
    @PostMapping("/delete/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Integer commentId,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        commentService.deleteComment(commentId, currentUser.getUsername());
        return ResponseEntity.ok().build();
    }
}
