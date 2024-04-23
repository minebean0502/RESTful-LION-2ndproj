package com.hppystay.hotelreservation.hotel.review;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;

import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final AuthenticationFacade auth;

    // 리뷰 생성
    public ReviewDto createReview(Long hotelId, ReviewDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        //TODO: 리뷰를 작성하려는 고객이 해당 호텔에서 구매 기록이 없는 경우 로직 추가?

        Review review = Review.builder()
                .member(member)
                .hotel(hotel)
                .content(dto.getContent())
                .score(dto.getScore())
                .build();

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    // 리뷰 수정
    public ReviewDto updateReview(Long hotelId, Long reviewId, ReviewDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.NOT_FOUND));

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.NOT_FOUND));

        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        // 호텔의 댓글이 맞는지
        if (!review.getHotel().equals(hotel)) {
            throw new GlobalException(GlobalErrorCode.MISMATCH);
        }

        //  // 리뷰를 수정하려는 사람이 리뷰 주인과 일치하지 않으면
        if (!review.getMember().getId().equals(member.getId())) {
            // 시스템 관리자가 아니라면
            if (!member.getRole().equals(MemberRole.ROLE_ADMIN)) {
                throw new GlobalException(GlobalErrorCode.MEMBER_MISMATCH);
            }
        }

        review.setContent(dto.getContent());
        review.setScore(dto.getScore());

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    // 리뷰 삭제
    public void deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.NOT_FOUND));

        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        // 리뷰를 삭제하려는 사람이 리뷰 주인과 일치하지 않으면
        if (!review.getMember().getId().equals(member.getId())) {
            // 시스템 관리자가 아니라면
            if (!member.getRole().equals(MemberRole.ROLE_ADMIN))
                throw new GlobalException(GlobalErrorCode.MEMBER_MISMATCH);
        }

        reviewRepository.delete(review);
    }
}
