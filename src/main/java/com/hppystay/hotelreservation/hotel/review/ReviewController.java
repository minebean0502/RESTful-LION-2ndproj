package com.hppystay.hotelreservation.hotel.review;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/{hotelId}/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public ReviewDto createReview(
            @PathVariable("hotelId")
            Long hotelId,
            @RequestBody
            ReviewDto dto
    ) {
        return reviewService.createReview(hotelId, dto);
    }

    // 대댓글 작성
    @PostMapping("{reviewId}")
    public ReviewDto replyReview(
            @PathVariable("hotelId")
            Long hotelId,
            @PathVariable("reviewId")
            Long reviewId,
            @RequestBody
            ReviewDto dto
    ) {
        return reviewService.replyReview(hotelId, reviewId, dto);
    }


    // 리뷰 리스트
    @GetMapping("/list")
    public List<ReviewDto> readAllReviews(
            @PathVariable("hotelId")
            Long hotelId
    ) {
        return reviewService.readAllReviews(hotelId);
    }

    @GetMapping("{reviewId}")
    public ReviewDto getReview(
            @PathVariable("hotelId")
            Long hotelId,
           @PathVariable("reviewId")
            Long reviewId
            ) {
        return reviewService.getReview(reviewId);

    }

    // 리뷰 수정
    @PutMapping("{reviewId}/update")
    public ReviewDto updateReview(
            @PathVariable("hotelId")
            Long hotelId,
            @PathVariable("reviewId")
            Long reviewId,
            @RequestBody
            ReviewDto dto
    ) {
        return reviewService.updateReview(hotelId, reviewId, dto);
    }

    @DeleteMapping("{reviewId}/delete")
    public void deleteReview(
            @PathVariable("hotelId")
            Long hotelId,
            @PathVariable("reviewId")
            Long reviewId
    ) {
        reviewService.deleteReview(reviewId);
    }
}
