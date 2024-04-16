package com.hppystay.hotelreservation.hotel.inquiry.controller;

import com.hppystay.hotelreservation.hotel.inquiry.dto.CommentDto;
import com.hppystay.hotelreservation.hotel.inquiry.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/hotel/inquiries/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/add/{inquiryId}")
    public String showCommentForm(@PathVariable("inquiryId") Integer inquiryId, Model model) {
        model.addAttribute("inquiryId", inquiryId);
        return "inquiries/comment-form";
    }

    @PostMapping("/submit")
    public String submitComment(
            //@AuthenticationPrincipal CustomUserDetails userDetails,
            CommentDto commentDto
    ) {
        //userDetails에서 사용자 ID를 가져옴.
        //Integer writerId = userDetails.getId();

        // 임시로 정적 설정.
        Integer writerId = 57;

        commentService.createComment(commentDto, writerId);

        return "redirect:/api/hotel/inquiries";
    }
}
