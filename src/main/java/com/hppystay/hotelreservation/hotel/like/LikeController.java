package com.hppystay.hotelreservation.hotel.like;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;

    // 좋아요 기능
   @PostMapping("{hotelId}")
    public void addLike(
            @PathVariable("hotelId")
            Long hotelId
   ) {
       likeService.addLike(hotelId);
   }
}
