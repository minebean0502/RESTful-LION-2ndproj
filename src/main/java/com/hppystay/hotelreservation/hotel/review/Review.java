package com.hppystay.hotelreservation.hotel.review;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.entity.BaseEntity;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String content; // 리뷰 내용

    private Integer depth;
    @Setter
    private double score; // 별점

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Review parentReview;

    @OneToMany(mappedBy = "parentReview", fetch = FetchType.LAZY)
    private List<Review> childReviews = new ArrayList<>();

    public static Review.ReviewBuilder customBuilder() {
        return builder().childReviews(new ArrayList<>());
    }
}
