package com.hppystay.hotelreservation.hotel.review;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class ReviewDto {
    private Long id;
    private Long memberId;
    private Long hotelId;
    private String nickname;
    private String email;
    private Integer depth;
    private String content;
    private double score; // 리뷰 점수
    private List<ReviewDto> childReviews;

    public static ReviewDto fromEntity(Review entity) {
        List<ReviewDto> childDtoList = entity.getChildReviews().stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());

        return new ReviewDto(
                entity.getId(),
                entity.getMember().getId(),
                entity.getHotel().getId(),
                entity.getMember().getNickname(),
                entity.getMember().getEmail(),
                entity.getDepth(),
                entity.getContent(),
                entity.getScore(),
                childDtoList
        );
    }
}
