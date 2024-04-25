package com.hppystay.hotelreservation.hotel.like;

import com.hppystay.hotelreservation.auth.entity.Member;
import com.hppystay.hotelreservation.common.exception.GlobalErrorCode;
import com.hppystay.hotelreservation.common.exception.GlobalException;
import com.hppystay.hotelreservation.common.util.AuthenticationFacade;
import com.hppystay.hotelreservation.hotel.entity.Hotel;
import com.hppystay.hotelreservation.hotel.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {
    private final LikeRepository likeRepository;
    private final HotelRepository hotelRepository;
    private final AuthenticationFacade auth;

    // 좋아요 기능
    public void addLike(Long hotelId) {
        Member member = auth.getCurrentMember();

        Hotel hotel = hotelRepository.findById(hotelId).orElseThrow(
                () -> new GlobalException(GlobalErrorCode.HOTEL_NOT_FOUND));

        if (!likeRepository.existsByMemberAndHotel(member, hotel)) {
            // 호텔의 like_count 증가
            hotel.setLike_count(hotel.getLike_count() + 1);
            likeRepository.save(new Like(member, hotel));

        } else {
            // 좋아요가 있는 상태에서 한번 더 좋아요 하면 좋아요 취소
            hotel.setLike_count(hotel.getLike_count() - 1);
            likeRepository.deleteByMemberAndHotel(member, hotel);
        }
    }
}








