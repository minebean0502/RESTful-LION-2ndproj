package com.hppystay.hotelreservation.hotel.review;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ReviewDto {
    private Long id;
    private Long memberId;
    private Long hotelId;
    @Setter
    private String content;
    @Setter
    private int score;

    public static ReviewDto fromEntity(Review entity) {
        return ReviewDto.builder()
                .id(entity.getId())
                .hotelId(entity.getHotel().getId())
                .memberId(entity.getMember().getId())
                .content(entity.getContent())
                .score(entity.getScore())
                .build();
    }
}
