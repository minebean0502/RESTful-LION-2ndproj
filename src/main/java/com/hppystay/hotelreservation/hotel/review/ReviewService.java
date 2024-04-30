package com.hppystay.hotelreservation.hotel.review;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.auth.entity.MemberRole;

import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;

import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.dto.HotelDto;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final AuthenticationFacade auth;

    // 리뷰 생성
    public ReviewDto createReview(Long hotelId, ReviewDto dto) {
        // 호텔 정보 가져오기
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        // 유저 정보 가져오기
        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        Review review = Review.customBuilder()
                .hotel(hotel)
                .member(member)
                .content(dto.getContent())
                .score(dto.getScore())
                .depth(0)
                .build();
        reviewRepository.save(review);

        // 해당 호텔의 평균 평점과 총 리뷰 개수 가져오기
        List<Object[]> result = hotelRepository.getHotelWithAll(hotelId);

        Object[] data = result.get(0);
        Double avgScore = (Double) data[0];
        Long reviewCount = (Long) data[1];

        hotel.setAvg_score(avgScore);
        hotel.setReview_count(reviewCount);
        hotelRepository.save(hotel);

        return ReviewDto.fromEntity(review);
    }

    // 작성된 리뷰에 답글 생성(대댓글) - 호텔 관리자 혹은 시스템 관리자만 답글 생성 가능
    public ReviewDto replyReview(Long hotelId, Long reviewId, ReviewDto dto) {
        // 호텔 정보 가져오기
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        // 유저 정보 가져오기
        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        // 리뷰 정보 가져오기
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.REVIEW_NOT_FOUND));

        // 호텔 관리자 혹은 시스템 관리자 인지
        if (!member.getRole().equals(MemberRole.ROLE_ADMIN) && !member.getId().equals(hotel.getManager().getId())) {
            throw new GlobalException(GlobalErrorCode.ROLE_UNAUTHORIZED);
        }

        Review replyReview = Review.customBuilder()
                .member(member)
                .hotel(hotel)
                .content(dto.getContent())
                .score(dto.getScore())
                .depth(1)
                .parentReview(review)
                .build();

        return ReviewDto.fromEntity(reviewRepository.save(replyReview));
    }

    //리뷰 리스트
    public List<ReviewDto> readAllReviews (
            Long hotelId
    ) {
        return reviewRepository.findAllByHotelId(hotelId).stream()
                .map(ReviewDto::fromEntity)
                .toList();
    }

    public ReviewDto getReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.REVIEW_NOT_FOUND));
        return ReviewDto.fromEntity(review);

    }
    // 리뷰 수정 - 리뷰 작성한 본인 혹은 시스템 관리자만 가능
    public ReviewDto updateReview(Long hotelId, Long reviewId, ReviewDto dto) {
        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.REVIEW_NOT_FOUND));

        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        // 호텔의 댓글이 맞는지
        if (!review.getHotel().equals(hotel)) {
            throw new GlobalException(GlobalErrorCode.HOTEL_MISMATCH);
        }

        //  // 리뷰를 수정하려는 사람이 리뷰 주인과 일치하지 않으면
        if (!review.getMember().getId().equals(member.getId())) {
            // 시스템 관리자가 아니라면
            if (!member.getRole().equals(MemberRole.ROLE_ADMIN)) {
                throw new GlobalException(GlobalErrorCode.ROLE_MISMATCH);
            }
        }

        review.setContent(dto.getContent());
        review.setScore(dto.getScore());

        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    // 리뷰 삭제 - 리뷰 작성한 본인 혹은 시스템 관리자만 가능
    public void deleteReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.REVIEW_NOT_FOUND));

        Member member = auth.getCurrentMember();
        log.info("auth account: {}", member.getEmail());

        // 리뷰를 삭제하려는 사람이 리뷰 주인과 일치하지 않으면
        if (!review.getMember().getId().equals(member.getId())) {
            // 시스템 관리자가 아니라면
            if (!member.getRole().equals(MemberRole.ROLE_ADMIN))
                throw new GlobalException(GlobalErrorCode.ROLE_MISMATCH);
        }

        reviewRepository.delete(review);
    }
}
